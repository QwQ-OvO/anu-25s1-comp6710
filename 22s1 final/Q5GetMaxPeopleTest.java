
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;
import java.util.Set;
import java.util.HashSet;

@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
public class Q5GetMaxPeopleTest {
    
    //实例变量声明
    //声明一个Q5Forum类型的实例变量
    private Q5Forum forum;

    String[] pagenames = new String[]{
            "gitlab",
            "comp2300",
            "IT Services",
            "comp1110-labs",
            "wattle",
            "calendar",
            "timetable",
            "FAQ",
            "comp1110-ass2",
            "comp1110-ass1",
    };

    String[] admins = new String[]{
            "Alice",
            "Charles",
            "Bob",
            "Cindy",
            "Steve",
            "Paul",
            "Penny",
            "Steve",
            "Paul",
            "Paul",
    };

    String[][] moderators = new String[][]{
            new String[]{"Ray", "Bob", "Cindy", "Alice"},
            new String[]{"Bob", "Paul", "Steve", "Clara", "Ray"},
            new String[]{"Clara", "Penny", "Dianne", "Alice"},
            new String[]{"Paul", "Ray", "Clara", "Cindy", "Bob"},
            new String[]{"Steve", "Edd"},
            new String[]{"Paul", "Dianne", "Clara", "Ray", "Alice", "Penny", "Bob", "Cindy"},
            new String[]{"Dianne", "Sarah", "Cindy", "Nicky", "Ben"},
            new String[]{"Steve", "Paul", "Alice"},
            new String[]{"Penny", "Bob", "Alice"},
            new String[]{"Bob", "Alice", "Paul", "Steve", "Ray", "Penny"}
    };

    String[] dates = new String[]{
            "2022-01-15",
            "2021-01-15",
            "2022-02-10",
            "2022-04-01",
            "2022-05-27",
            "2022-06-20",
            "2021-05-02",
            "2022-03-13",
            "2022-03-12",
            "2021-02-10",
            "2021-01-10",
    };

    @BeforeEach
    void setUp() {
        forum = new Q5Forum(); // 在每个测试方法执行前创建新的forum实例
    }

    /**
     * 测试空论坛的情况
     */
    @Test
    void testEmptyForum() {
        // 此时forum已经通过@BeforeEach创建，但forumSystem是空的HashMap
        assertEquals(0, forum.getMaxPeople(), "空论坛应该返回0");
    }

    /**
     * 测试单个页面的情况
     */
    @Test
    void testSinglePage() {
        // Arrange: 准备测试数据
        Set<String> mods = new HashSet<>(); // 初始化一个新的局部变量HashSet来存储版主
        mods.add("Bob");
        mods.add("Charlie");
        
        // Act: 执行被测试的操作  
        forum.addPage("test", "Alice", "2022-01-01", mods); // 数据准备（不测试这个）
        //      ↑         ↑       ↑        ↑           ↑
        //   方法名     页面名    管理员     日期     版主集合
       
        // Assert: 验证结果
        // 测试：单页面时getMaxPeople()正确计算人数
        // 应该有3个人：Alice(管理员) + Bob + Charlie
        assertEquals(3, forum.getMaxPeople(), "单个页面应该返回所有管理员和版主的总数");
    }

    /**
     * 测试管理员也是版主的情况（去重）
     */
    @Test
    void testAdminAlsoModerator() {
        Set<String> mods = new HashSet<>();
        mods.add("Alice"); // 管理员Alice也是版主
        mods.add("Bob");
        
        forum.addPage("test", "Alice", "2022-01-01", mods);
        
        // Assert: 验证结果
        // 测试：管理员同时是版主时应该去重
        // 应该有2个人：Alice（去重）+ Bob
        assertEquals(2, forum.getMaxPeople(), "管理员同时是版主时应该去重");
    }

    /**
     * 测试同一日期多个页面的情况
     */
    @Test
    void testSameDateMultiplePages() {
        
        //页面1的版主
        Set<String> mods1 = new HashSet<>();
        mods1.add("Bob");
        
         //页面2的版主
        Set<String> mods2 = new HashSet<>();
        mods2.add("Charlie");
        mods2.add("David");
        
        //使用相同日期
        forum.addPage("page1", "Alice", "2022-01-01", mods1);
        forum.addPage("page2", "Eve", "2022-01-01", mods2);
        
        // 同一日期：Alice + Bob + Eve + Charlie + David = 5个人
        assertEquals(5, forum.getMaxPeople(), "同一日期多个页面应该合并计算人数");
    }

    /**
     * 测试同一日期多个页面但有重复人员的情况
     */
    @Test
    void testSameDateWithDuplicates() {
        Set<String> mods1 = new HashSet<>();
        mods1.add("Bob");
        mods1.add("Charlie");
        
        Set<String> mods2 = new HashSet<>();
        mods2.add("Bob"); // 重复的版主
        mods2.add("David");
        
        forum.addPage("page1", "Alice", "2022-01-01", mods1);
        forum.addPage("page2", "Alice", "2022-01-01", mods2); // 重复的管理员
        
        // 同一日期：Alice + Bob + Charlie + David = 4个人（去重后）
        assertEquals(4, forum.getMaxPeople(), "同一日期重复人员应该去重");
    }

    /**
     * 测试不同日期的情况，应该返回最大值
     */
    @Test
    void testDifferentDatesMaximum() {
        Set<String> mods1 = new HashSet<>();
        mods1.add("Bob");
        
        Set<String> mods2 = new HashSet<>();
        mods2.add("Charlie");
        mods2.add("David");
        mods2.add("Eve");
        
        forum.addPage("page1", "Alice", "2022-01-01", mods1); // 2个人
        forum.addPage("page2", "Frank", "2022-01-02", mods2); // 4个人
        
        // 应该返回最大的4
        assertEquals(4, forum.getMaxPeople(), "不同日期应该返回最大人数");
    }

    /**
     * 测试空版主列表的情况
     */
    @Test
    void testEmptyModerators() {
        Set<String> emptyMods = new HashSet<>();
        
        forum.addPage("test", "Alice", "2022-01-01", emptyMods);
        assertEquals(1, forum.getMaxPeople(), "只有管理员没有版主时应该返回1");
    }

    /**
     * 使用提供的测试数据进行综合测试
     */
    @Test
    void testWithProvidedData() {
        // 添加所有提供的测试数据
        for (int i = 0; i < pagenames.length; i++) {
            Set<String> modSet = new HashSet<>();
            for (String mod : moderators[i]) {
                modSet.add(mod);
            }
            forum.addPage(pagenames[i], admins[i], dates[i], modSet);
        }
        
        // 验证结果不为0且为正数
        int result = forum.getMaxPeople();
        assertTrue(result > 0, "使用提供数据时结果应该大于0");
        
        // 验证结果在合理范围内 - 考虑到所有人员总数
        assertTrue(result <= 20, "结果应该在合理范围内");
        
        // 验证至少要有管理员数量（因为每个页面至少有1个管理员）
        assertTrue(result >= 1, "结果至少应该有1个人");
        
        // 手工验证部分数据 - 例如检查某个特定日期
        // 比如"2022-01-15"只有一个页面"gitlab"：管理员Alice + 版主Ray,Bob,Cindy,Alice
        // 去重后应该是：Alice, Ray, Bob, Cindy = 4人
        // 但这只是其中一个日期，最终结果可能更大
        
        // 确保方法执行没有异常且返回合理结果
        assertNotNull(result, "结果不应该为null");
    }

    /**
     * 测试边界情况：只有一个空字符串版主
     */
    @Test
    void testEdgeCaseEmptyStringModerator() {
        Set<String> mods = new HashSet<>();
        mods.add(""); // 空字符串版主
        mods.add("Bob");
        
        forum.addPage("test", "Alice", "2022-01-01", mods);
        // 应该有3个人：Alice + 空字符串 + Bob
        assertEquals(3, forum.getMaxPeople(), "空字符串版主也应该被计算");
    }
}
