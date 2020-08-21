package zmz.netdisk.controller;

import com.mr.response.ServerResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zmz.netdisk.domain.DirectoryListResult;
import zmz.netdisk.domain.FileObject;
import zmz.netdisk.domain.FolderObject;

import java.io.File;
import java.io.FileFilter;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/file")
@Api(tags = "文件查询相关api")
public class QueryController {
    @Value("${file.upload.place}")
    private String rootPath;



    @ApiOperation(value = "列出当前目录下的所有文件和文件夹" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "path", value = "全路径名",required = true,dataType = "string", example = "/SecondMarket/"),
    })
    @GetMapping("/list")
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
                fileObject.setNameOfFullPath(f.getAbsolutePath().substring(rootPath.length()));
                result.addFile(fileObject);
            }else if(f.isDirectory())
            {
                FolderObject folderObject = new FolderObject();
                folderObject.setName(f.getName());
                folderObject.setNameOfFullPath(f.getAbsolutePath().substring(rootPath.length()));
                folderObject.setTime(f.lastModified());
                String[] list = f.list();
                if(list == null || list.length == 0)
                {
                    folderObject.setEmptyFolder(true);
                }else
                {
                    folderObject.setEmptyFolder(false);
                }
                result.addFloder(folderObject);
            }
        }

        return ServerResponse.success(result);
    }
}
