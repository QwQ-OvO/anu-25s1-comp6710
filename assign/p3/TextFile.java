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
    /** 存储每行的单词列表，外层列表的每个元素对应文件的一行 */
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
                    lines.add(line);

                    String[] wordsArray = line.split(" +");
                    ArrayList<String> lineWords = new ArrayList<>();

                    for (String word : wordsArray) {
                        if (!word.isEmpty()) {
                            lineWords.add(word);
                            allWords.add(word);
                            wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
                        }
                    }

                    wordInLines.add(lineWords);
                }
            }
        }
        catch (IOException e) {
            throw new RuntimeException("Error reading file: " + file.getPath(), e);
        }
    }
}
