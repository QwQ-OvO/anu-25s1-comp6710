package PASS.Q5FileSystem_Practice;

import java.io.File;
import java.util.List;

/**
 * Represents a text file in a file system given an abstract path name
 */
public class FsTextFile implements FsNode {

    private final File file;
    // 提示：需要存储文件引用和文件内容（行列表）
    
    /**
     * Creates a new representation of a text file in
     * a file system given an abstract path name
     * @param file an abstract pathname pointing to a text file
     */
    public FsTextFile(File file, File file1) {
        this.file = file;
        // 提示：使用BufferedReader读取文件所有行
    }
    
    @Override
    public File getUnderlyingFile() {
        return file;
    }
    
    @Override
    public List<FsNode> allNodes() {
        // TODO: 文本文件只包含自己
        return null;
    }
    
    /**
     * @return the lines of text in this file
     */
    public List<String> getLines() {
        // TODO: 返回文件行内容的副本
        return null;
    }
    
    @Override
    public boolean equals(Object obj) {
        // TODO: 实现相等性判断
        // 提示：需要检查文件名和所有行内容都相等
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