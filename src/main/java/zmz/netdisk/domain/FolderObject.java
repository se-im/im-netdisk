package zmz.netdisk.domain;

import lombok.Data;

@Data
public class FolderObject
{
    private String name;
    //文件夹全路径名
    private String nameOfFullPath;
    private Long time;
    private Boolean emptyFolder;
}
