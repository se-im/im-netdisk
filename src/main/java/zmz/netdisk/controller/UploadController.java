package zmz.netdisk.controller;

import com.mr.response.ServerResponse;
import com.mr.response.error.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zmz.netdisk.domain.DirectoryListResult;
import zmz.netdisk.domain.FileObject;
import zmz.netdisk.domain.FolderObject;
import zmz.netdisk.util.IpTimeStamp;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/file")
@Api(tags = "文件上传相关api")
public class UploadController
{

    @Value("${file.upload.place}")
    private String rootPath;

    @Value("${im.headPic.location}")
    private String imPicLocation;


    @Value("${file.server.name}")
    private String domain;



    @ApiOperation(value = "上传文件头像文件" )
    @PostMapping("/upload/headpic")
    public ServerResponse<String> uploadHeadPic(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException
    {

        log.info("收到文件上传头像请求");

        IpTimeStamp ipTimeStamp = new IpTimeStamp(InetAddress.getLocalHost().getHostAddress());
        log.info("用户头像上传" );
        log.info("原文件名： " , file.getOriginalFilename());
        //本地存储的文件名
        String newFileName = ipTimeStamp.getIpTimeRand();
        //文件后缀名
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        File dest = new File(rootPath + imPicLocation, newFileName + "." + suffix);
        System.out.println(dest.getAbsoluteFile());
        if(!dest.exists())
        {
            dest.createNewFile();
        }

        log.info("转储文件： " + dest.getAbsolutePath());
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String fileNetLocation = "http://" + domain +":8010/res/file/pic/" + dest.getName();

        return ServerResponse.success(fileNetLocation);

    }




    @ApiOperation(value = "上传文件，文件名不变，服务端有的话会覆盖" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filePath", value = "上传的路径",required = true,dataType = "string", example = "/pic/"),
    })
    @PostMapping("/upload/small")
    public ServerResponse<String> upload(@RequestParam("files[]") MultipartFile[] files, String filePath,HttpServletRequest request)
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

        return ServerResponse.success("success");

    }




}

