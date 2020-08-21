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

import java.io.File;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/file")
@Api(tags = "文件修改相关api")
public class UpdateController
{

    @Value("${file.upload.place}")
    private String rootPath;



    @ApiOperation(value = "新建文件夹" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dir", value = "新建的文件夹，根路径开始的全路径名",required = true,dataType = "string", example = "/pic/"),
    })
    @PutMapping("/mkdir")
    public ServerResponse<String> makeNewDir(String dir) throws BusinessException
    {

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


    @ApiOperation(value = "删除文件" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filename", value = "删除的文件全路径名",required = true,dataType = "string", example = "/pic/a.txt"),
    })
    @DeleteMapping("/delete")
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
