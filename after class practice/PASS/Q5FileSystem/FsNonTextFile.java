import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents some file that is not a directory nor a
 * text file in the file system.
 */
public class FsNonTextFile implements FsNode {
    
    private final File file;
    
    /**
     * Creates a new representation of a file that is not a
     * text file in a file system given an abstract path name
     * @param file an abstract pathname pointing to a file
     */
    public FsNonTextFile(File file) {
        this.file = file;
    }
    
    @Override
    public File getUnderlyingFile() {
        return file;
    }
    
    @Override
    public List<FsNode> allNodes() {
        List<FsNode> result = new ArrayList<>();
        result.add(this); // Non-text files only contain themselves
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        FsNonTextFile other = (FsNonTextFile) obj;
        
        // Non-text files are equal if their names are equal
        return file.getName().equals(other.file.getName());
    }
    
    @Override
    public int hashCode() {
        return file.getName().hashCode();
    }
    
    @Override
    public String toString() {
        return "FsNonTextFile{" + file.getAbsolutePath() + "}";
    }
} 