package zmz.netdisk.controller;


import com.mr.response.error.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
@Slf4j
@CrossOrigin
@Api(tags = "文件下载相关api")
public class DownloadController {


    @Value("${file.upload.place}")
    private String rootPath;


    @ApiOperation(value = "下载文件" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileName", value = "文件全路径名",required = true,dataType = "string", example = "/SecondMarket/pom.xml"),
    })
    @GetMapping(value = "/download")
    public ResponseEntity<byte[]> downloadFile(String fileName) throws IOException, BusinessException {

        if(StringUtils.isEmpty(fileName))
        {
            throw new BusinessException("文件不能为空");
        }
        File f = new File(rootPath, fileName);
        if(!f.exists())
        {
            throw new BusinessException("文件不存在");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        // 5.返回下载
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(f),
                headers, HttpStatus.OK);

    }

}
