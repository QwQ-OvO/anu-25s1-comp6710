package PASS.Q5FileSystem_Practice;

import java.io.File;
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
        // TODO: 非文本文件只包含自己
        return null;
    }
    
    @Override
    public boolean equals(Object obj) {
        // TODO: 实现相等性判断
        // 提示：非文本文件只需要文件名相等即可
        return false;
    }
    
    @Override
    public int hashCode() {
        // TODO: 实现hashCode方法
        return 0;
    }
    
    @Override
    public String toString() {
        // TODO: 可选，用于调试
        return super.toString();
    }
} 