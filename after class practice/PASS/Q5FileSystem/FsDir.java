import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a directory in a file system
 */
public class FsDir implements FsNode {
    
    private final File directory;
    private final List<FsNode> children;
    
    /**
     * Creates a new representation of a directory in
     * a file system given an abstract path name
     * @param dir an abstract pathname pointing to a directory
     */
    public FsDir(File dir) {
        this.directory = dir;
        this.children = new ArrayList<>();
        
        if (dir.exists() && dir.isDirectory()) {
            try {
                // Use Files.list() instead of listFiles() as required
                List<Path> paths = Files.list(dir.toPath())
                    .sorted((p1, p2) -> p1.getFileName().toString().compareTo(p2.getFileName().toString()))
                    .collect(Collectors.toList());
                
                for (Path path : paths) {
                    children.add(FsNode.createNode(path.toFile()));
                }
            } catch (IOException e) {
                // If we can't read the directory, leave children empty
                System.err.println("Warning: Could not read directory " + dir.getAbsolutePath());
            }
        }
    }
    
    @Override
    public File getUnderlyingFile() {
        return directory;
    }
    
    @Override
    public List<FsNode> allNodes() {
        List<FsNode> result = new ArrayList<>();
        result.add(this); // Add current directory first (pre-order)
        
        // Add all children and their descendants
        for (FsNode child : children) {
            result.addAll(child.allNodes());
        }
        
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        FsDir other = (FsDir) obj;
        
        // Check if names are equal
        if (!directory.getName().equals(other.directory.getName())) {
            return false;
        }
        
        // Check if all children are equal
        if (children.size() != other.children.size()) {
            return false;
        }
        
        for (int i = 0; i < children.size(); i++) {
            if (!children.get(i).equals(other.children.get(i))) {
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public int hashCode() {
        int result = directory.getName().hashCode();
        result = 31 * result + children.hashCode();
        return result;
    }
    
    @Override
    public String toString() {
        return "FsDir{" + directory.getAbsolutePath() + "}";
    }
} 