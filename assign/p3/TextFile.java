package p3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TextFile {

    /** 存储原始文件引用 */
    private File file;
    /** 存储文件的所有行 */
    private ArrayList<String> lines;
    /**
     * 存储每行的单词列表，外层列表的每个元素对应文件的一行
     * 嵌套列表用于保持文件的二维结构（行和单词）
     */
    private ArrayList<ArrayList<String>> wordInLines;
    /** 存储单词及其出现次数的映射关系 */
    private Map<String, Integer> wordCounts;
    /** 按文件中出现的顺序存储所有单词 */
    private ArrayList<String> allWords;

    /**
     * 构造一个 TextFile 对象，读取并处理指定文件的内容。
     *
     * @param file 要处理的文本文件
     * @throws RuntimeException 如果文件读取失败，将 IOException 转换为 RuntimeException
     */
    public TextFile(File file) {
        // 初始化实例变量，存储文件引用
        this.file = file;
        // 创建存储文件行内容的列表
        this.lines = new ArrayList<>();
        // 创建存储每行单词的嵌套列表
        this.wordInLines = new ArrayList<>();
        // 创建存储单词计数的映射
        this.wordCounts = new HashMap<>();
        // 创建存储所有单词序列的列表
        this.allWords = new ArrayList<>();

        try {
            // 使用 try-with-resources 创建 BufferedReader
            // BufferedReader 提供了缓冲功能，提高读取效率
            // FileReader 是基础字符输入流，用于读取文件
            // 当 try 块结束时，reader 会自动关闭，无需手动 close()
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

                // 用于存储每次读取的行
                String line;
                // readLine() 读取一行文本，不包括行终止符
                // 当到达文件末尾时返回 null
                // 括号确保先执行赋值，再进行 null 比较
                while ((line = reader.readLine()) != null) {
                    // 将当前行添加到行列表中
                    lines.add(line);

                    // 使用正则表达式 " +" 分割行文本
                    // 该正则匹配一个或多个连续空格，处理连续空格情况
                    String[] wordsArray = line.split(" +");

                    // 创建用于存储当前行单词的列表
                    ArrayList<String> lineWords = new ArrayList<>();

                    // 遍历当前行中的所有单词
                    for (String word : wordsArray) {
                        // 跳过空单词
                        if (!word.isEmpty()) {
                            // 将单词添加到当前行的单词列表
                            lineWords.add(word);
                            // 将单词添加到全局单词列表，保持文件中的顺序
                            allWords.add(word);
                            // 更新单词计数映射
                            // getOrDefault 获取单词当前计数，如果不存在则返回默认值 0
                            // 然后计数加 1 并存回映射
                            wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
                        }
                    }
                    // 将处理完的当前行单词列表添加到嵌套列表中
                    wordInLines.add(lineWords);
                }
            } // 此处 BufferedReader 自动关闭
        }
        // 捕获可能的 IOException（受检异常）
        catch (IOException e) {
            // 将受检异常转换为非受检异常 RuntimeException
            // 同时保留原始异常信息和堆栈跟踪
            // 添加文件路径信息以便更好地诊断问题
            throw new RuntimeException("Error reading file: " + file.getPath(), e);
        }
    }
}
