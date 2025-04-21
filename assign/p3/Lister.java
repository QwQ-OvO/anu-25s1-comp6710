package p3;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;

public class Lister {

    static String[] getFiles(File directory) {
        // 参数验证：确保目录存在且真的是目录
        if (!directory.exists() || !directory.isDirectory()) {
            return new String[0]; // 返回空数组作为错误处理
        } else {
            // 创建动态数组用于收集文件名
            ArrayList<String> files = new ArrayList<>();
            // 调用辅助方法递归收集所有文件
            collectFiles(directory, files);

            // 将动态数组转换为字符串数组并返回
            return files.toArray(new String[0]);
            // 注：files.toArray(new String[0]) 比 files.toArray(new String[files.size()]) 更高效
            // 因为JVM会根据ArrayList的大小自动创建合适大小的数组
        }

    }

    /**
     *在设计递归辅助方法时，参数选择非常重要，它直接影响方法的功能、灵活性和性能。
     *
     * 常见参数类型：
     * 1.当前状态参数
     * 例如：当前处理的目录 File directory
     * 作用：表示递归过程中当前正在处理的对象
     * 2.累积结果参数
     * 例如：收集结果的集合 ArrayList<String> files
     * 作用：跨递归调用共享和累积结果
     * 3.状态追踪参数
     * 例如：当前深度、访问路径、已处理节点集合等
     * 作用：维护递归过程中的状态信息
     * 4.控制参数
     * 例如：最大深度、过滤条件、标志位等
     * 作用：控制递归行为或提前终止
     *
     * 参数选择原则:
     * 最小充分原则, 状态共享与隔离, 可变性与不可变性, 通用性与专用性
     */
    static void collectFiles(File directory, ArrayList<String> files) {
        // 获取目录中的所有内容
        // listFiles() - 获取当前目录中的所有条目
        File[] filesArray = directory.listFiles(); //filesArray临时存储当前目录下的文件和子目录

        // 遍历目录中的每一项
        for (File file : filesArray) {
            // 如果是文件，将文件名添加到结果集
            // isFile() - 检查当前对象是否是文件
            // getName() - 获取文件名（不包括路径）
            // add() - 将文件名添加到结果集ArrayList<String> files
            if (file.isFile()) {
                files.add(file.getName());
            } else if (file.isDirectory()) {
                // 如果是目录，递归处理该目录
                collectFiles(file, files);
            }
        }
    }

    static Ancestor[] getAncestors(File directory) {
        // 参数验证：确保目录存在且真的是目录
        if (!directory.exists() || !directory.isDirectory()) {
            return new Ancestor[0];
        }
        ArrayList<Ancestor> ancestorsList = new ArrayList<>();

        // 获取当前目录的父目录
        File parent = directory.getParentFile();

        // 遍历所有父目录直到根目录
        while (parent != null) {
            // 创建新的祖先对象并添加到列表
            ancestorsList.add(new Ancestor(parent));
            // 移动到下一个父目录
            parent = parent.getParentFile();
        }

        // 按照到根目录的距离排序
        ancestorsList.sort(Comparator.comparingInt(Ancestor::distance));

        // 转换列表为数组并返回
        return ancestorsList.toArray(new Ancestor[0]);


    }
}
