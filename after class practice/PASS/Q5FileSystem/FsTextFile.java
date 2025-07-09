import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a text file in a file system given an abstract path name
 */
public class FsTextFile implements FsNode {
    
    private final File file;
    private final List<String> lines;
    
    /**
     * Creates a new representation of a text file in
     * a file system given an abstract path name
     * @param file an abstract pathname pointing to a text file
     */
    public FsTextFile(File file) {
        this.file = file;
        this.lines = new ArrayList<>();
        
        if (file.exists() && file.isFile()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            } catch (IOException e) {
                // If we can't read the file, leave lines empty
                System.err.println("Warning: Could not read file " + file.getAbsolutePath());
            }
        }
    }
    
    @Override
    public File getUnderlyingFile() {
        return file;
    }
    
    @Override
    public List<FsNode> allNodes() {
        List<FsNode> result = new ArrayList<>();
        result.add(this); // Text files only contain themselves
        return result;
    }
    
    /**
     * @return the lines of text in this file
     */
    public List<String> getLines() {
        return new ArrayList<>(lines);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        FsTextFile other = (FsTextFile) obj;
        
        // Check if names are equal
        if (!file.getName().equals(other.file.getName())) {
            return false;
        }
        
        // Check if all lines are equal
        return lines.equals(other.lines);
    }
    
    @Override
    public int hashCode() {
        int result = file.getName().hashCode();
        result = 31 * result + lines.hashCode();
        return result;
    }
    
    @Override
    public String toString() {
        return "FsTextFile{" + file.getAbsolutePath() + ", lines=" + lines.size() + "}";
    }
} 