package zmz.netdisk.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DirectoryListResult {
    private List<FileObject> files;
    private List<FolderObject> floders;

    public DirectoryListResult() {
        files = new ArrayList<>();
        floders = new ArrayList<>();
    }

    public void addFile(FileObject fileObject)
    {
        this.files.add(fileObject);
    }

    public void addFloder(FolderObject folderObject)
    {
        this.floders.add(folderObject);
    }
}
