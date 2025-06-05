package comp1110.exam;

import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
public class Q5GetMaxUsersTest {
    // FIXME add one or more JUnit tests for the getMaxUsers() method of the Q5FileSystem class

    String[] dates = new String[]{
            "10-04-2022",
            "10-04-2021",
            "10-02-2022",
            "04-01-2021",
            "26-05-2022",
            "20-05-2022",
            "02-01-2021",
            "18-03-2022",
            "13-04-2022",
            "02-01-2021"
    };

    String[] owners = new String[]{
            "Alice",
            "Bob",
            "Bob",
            "Cindy",
            "Steve",
            "Paul",
            "Steve",
            "Steve",
            "Penny",
            "Paul",
    };

    String[][] users = new String[][]{
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

    String[] filenames = new String[]{
            "Assignment 1",
            "Homework",
            "Arboretum Skeleton",
            "Philosophy Essay",
            "Garbage Collection",
            "Draft Exam",
            "game.jar",
            "notes",
            "calendar",
            "Recipes"
    };
}