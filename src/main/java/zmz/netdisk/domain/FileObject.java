package zmz.netdisk.domain;

import lombok.Data;

@Data
public class FileObject {
    //文件名
    private String name;
    //文件最后修改时间
    private Long time;
    //文件大小，单位b
    // size/1024  结果单位是KB
    private Long size;
}
