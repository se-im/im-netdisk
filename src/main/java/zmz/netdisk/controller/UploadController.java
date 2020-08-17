package zmz.netdisk.controller;

import com.mr.response.ServerResponse;
import com.mr.response.error.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import zmz.netdisk.domain.DirectoryListResult;
import zmz.netdisk.domain.FileObject;
import zmz.netdisk.domain.FloderObject;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

@RestController
@Slf4j
@CrossOrigin
public class UploadController
{

    @Value("${file.upload.place}")
    private String rootPath;


    @RequestMapping("/upload/small")
    public String upload(@RequestParam("files[]") MultipartFile[] files, String filePath,HttpServletRequest request)
    {

        log.info("收到文件上传请求, 路径为 {}", filePath);
        if(StringUtils.isNotEmpty(filePath))
        {
            if(filePath.charAt(filePath.length()-1) != '/')
            {
                filePath  = filePath + "/";
            }
        }else
        {
            filePath = "/";
        }

        for (MultipartFile file : files)
        {

            String fileName = file.getOriginalFilename();
            if (fileName.equals(""))
                continue;
            log.info("文件名：{}", fileName);

            File dest = new File(rootPath , filePath + fileName);
            try {
                file.transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "success";

    }

    @RequestMapping("/list")
    public ServerResponse<DirectoryListResult> listFiles(String path)
    {
        String destPath = rootPath.substring(0, rootPath.length() - 1) + path;
        File paramFile = new File(destPath);
        if(!paramFile.isDirectory())
        {
            return ServerResponse.error("非法路径！");
        }
        File[] files = paramFile.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if(pathname.getName().startsWith("."))
                    return false;
                else
                    return true;
            }
        });

        DirectoryListResult result = new DirectoryListResult();

        for(File f: files)
        {
            if(f.isFile())
            {
                FileObject fileObject = new FileObject();
                fileObject.setName(f.getName());
                fileObject.setTime(f.lastModified());
                fileObject.setSize(f.length());
                result.addFile(fileObject);
            }else if(f.isDirectory())
            {
                FloderObject floderObject = new FloderObject();
                floderObject.setName(f.getName());
                floderObject.setTime(f.lastModified());
                result.addFloder(floderObject);
            }
        }

        return ServerResponse.success(result);
    }

    @RequestMapping("/mkdir")
    public ServerResponse<String> makeNewDir(String dir) throws BusinessException {

        if(StringUtils.isEmpty(dir))
        {
            throw new BusinessException("文件夹不能为空");
        }
        File file = new File(rootPath, dir);
        if(file.exists())
        {
            throw new BusinessException("文件夹已存在");
        }
        if(file.mkdir())
        {
            return ServerResponse.success("创建成功");
        }else
        {
            throw new BusinessException("创建文件夹失败");
        }
    }


    @RequestMapping("/delete")
    public ServerResponse<String> deleteFile(String fileName) throws BusinessException {
        if(StringUtils.isEmpty(fileName))
        {
            throw new BusinessException("文不能为空");
        }
        File f = new File(rootPath, fileName);
        if(!f.exists())
        {
            throw new BusinessException("文不存在");
        }

        boolean delete = f.delete();
        if(delete)
        {
            return ServerResponse.success("删除成功");
        }else
        {
            return ServerResponse.error("删除失败");
        }
    }
}

