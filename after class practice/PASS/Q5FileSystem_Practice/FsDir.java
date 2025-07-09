package PASS.Q5FileSystem_Practice;

import java.io.File;
import java.util.List;

/**
 * Represents a directory in a file system
 */
public class FsDir implements FsNode {
    
    // private final File file;
    // 提示：需要存储目录引用和子节点列表
    
    /**
     * Creates a new representation of a directory in
     * a file system given an abstract path name
     * @param dir an abstract pathname pointing to a directory
     */
    public FsDir(File dir) {
        // TODO: 实现构造函数
        // 重要提示：
        // 1. 不能使用File.listFiles()，要使用Files.list()
        // 2. 需要按字母顺序排序子文件/目录
        // 3. 对每个子项调用FsNode.createNode()创建子节点
    }
    
    @Override
    public File getUnderlyingFile() {
        // TODO: 返回底层目录对象
        return null;
    }
    
    @Override
    public List<FsNode> allNodes() {
        // TODO: 实现前序深度优先搜索
        // 提示：
        // 1. 先将自己添加到结果列表
        // 2. 然后对每个子节点递归调用allNodes()并添加到结果
        return null;
    }
    
    @Override
    public boolean equals(Object obj) {
        // TODO: 实现相等性判断
        // 提示：需要检查目录名相等 + 所有子节点都相等
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