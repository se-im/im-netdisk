package zmz.netdisk.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@RestController
public class UploadController
{

    @Value("${file.upload.place}")
    private String filePath;


    @RequestMapping("/upload")

    public String upload(@RequestParam("files[]") MultipartFile[] files, HttpServletRequest request)
    {

        for (MultipartFile file : files)
        {

            String fileName = file.getOriginalFilename();
            if (fileName.equals(""))
                continue;
            System.out.println(fileName);

            File dest = new File(filePath + fileName);
            try {
                file.transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "success";

    }
}

