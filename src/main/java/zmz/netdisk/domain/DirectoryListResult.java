package zmz.netdisk.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DirectoryListResult {
    private List<FileObject> files;
    private List<FloderObject> floders;

    public DirectoryListResult() {
        files = new ArrayList<>();
        floders = new ArrayList<>();
    }

    public void addFile(FileObject fileObject)
    {
        this.files.add(fileObject);
    }

    public void addFloder(FloderObject floderObject)
    {
        this.floders.add(floderObject);
    }
}
