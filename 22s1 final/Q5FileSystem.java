package comp1110.exam;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This class represents a simplified filesystem.
 * Each file has an owner, a date that it was created, a set of userids that have permission to
 * access the file and a filename.
 * Each file is uniquely identified by its filename.
 * Each user is uniquely identified by an userid.
 * Owners are also users.
 */
public class Q5FileSystem {

    //自定义内部类表示单个文件
    //不使用private省略getter方法
    public static class File{
        String fileName;
        String fileOwner;
        String fileDate;
        Set<String> filePermissions;

        public File(String fileName, String fileOwner, String fileDate, Set<String> filePermissions) {
            this.fileName = fileName;
            this.fileOwner = fileOwner;
            this.fileDate = fileDate;
            this.filePermissions = filePermissions;
        }
    }

    // 外部类的属性和构造函数
    public Map<String, File> fileSystem;
    public Q5FileSystem() {
        this.fileSystem = new HashMap<>();
    }

    /**
     * Add a new file to the filesystem. If the given file name exists, do not modify this
     * filesystem.
     *
     * @param filename    the name of the file.
     * @param owner       the userid of the owner of the file.
     * @param date        the date this file was created in the format "DD-MM-YYYY".
     * @param permissions the userids of users permitted to access the file.
     * @return true if the file was added, false if the file was not added (because a file with that
     * name already exists)
     */
    public boolean addFile(String filename, String owner, String date, Set<String> permissions) {
        if (filename == null || filename.isEmpty()) {
            return false;
        }
        if (fileSystem.containsKey(filename)) {
            return false;
        }
        File newFile = new File(filename, owner, date, permissions);
        fileSystem.put(filename, newFile);
        return true;
    }

    /**
     * Remove the file with the given name from the filesystem. If no file with the given name
     * exists, do not modify this filesystem.
     *
     * @param filename the name of the file to be removed.
     * @return true if removal was successful, otherwise false.
     */
    public boolean deleteFile(String filename) {
        if (filename == null || filename.isEmpty()) {
            return false;
        }
//        if (!fileSystem.containsKey(filename)) {
//            return false;
//        }
        return fileSystem.remove(filename) != null;
    }

    /**
     * @return the total number of files in this filesystem.
     */
    public int getFileCount() {
        return fileSystem.size();
    }

    /**
     * Get the names of all files that are owned by the given userid. If the given userid does not
     * own any files, return an empty set.
     *
     * @param userid the userid of an owner.
     * @return the names of all files that are owned by the given userid.
     */
    public Set<String> getFilesOwnedBy(String userid) {
        Set<String> filesOwned = new HashSet<>();

        if (userid == null || userid.isEmpty()) {
            return filesOwned;
        }

        for (File file : fileSystem.values()) {
            if (file.fileOwner.equals(userid)) {
                filesOwned.add(file.fileName);
            }
        }
        return filesOwned;
    }

    /**
     * Get the total number of unique users across all files in this filesystem. Each user is only
     * counted once even if they have permission to access multiple files. Owners are included in
     * this count.
     *
     * @return the number of unique users across all files.
     */
    public int getNumUsers() {
        if (fileSystem.isEmpty()) {
            return 0;
        }
        Set<String> users = new HashSet<>();
        for (File file : fileSystem.values()) {
            users.add(file.fileOwner);
            users.addAll(file.filePermissions); // addAll添加一个集合中的所有元素
        }
        return users.size();
    }

    /**
     * Get the names of all files in which the given userid is either an owner or has permission to
     * access the file. If the given userid does not own or have access to any file, return an empty
     * set.
     *
     * @param userid the userid of a user.
     * @return the names of all files that the given userid either owns or has permission to access.
     * If this userid does not own or have permission to access any files, return the empty set.
     */
    public Set<String> getFiles(String userid) {
        Set<String> allFileName = new HashSet<>();

        //null 检查避免空指针异常
        if (userid == null || userid.isEmpty()) {
            return allFileName;
        }

        for (File file : fileSystem.values()) {
            // 谨记filePermissions是Set<String>
            if (userid.equals(file.fileOwner) || file.filePermissions.contains(userid)) {
                allFileName.add(file.fileName);
            }
        }
        return allFileName;
    }

    /**
     * Gets the greatest number of files that any userid either owns or has permission to access. If
     * an userid is both an owner and has permission to access the same file, that file only counts
     * once.
     *
     * @return the maximum number of files for any userid.
     */
    public int getMaxFiles() {
        if (fileSystem == null || fileSystem.isEmpty()) {
            return 0;
        }
        Set<String> allUsers = new HashSet<>();
        for (File users : fileSystem.values()) {
            allUsers.add(users.fileOwner);
            allUsers.addAll(users.filePermissions);
        }

        int maxFileNum = 0;
        for (String user: allUsers) {
            // getFiles() 是文件系统的方法，在类内部可以直接调用；String 类没有这个方法。所以写法如下
            int filesCount = getFiles(user).size();
            if (filesCount > maxFileNum) {
                maxFileNum = filesCount;
            }
        }
        return  maxFileNum;
    }

    /**
     * Get the names of all files in the filesystem that were created strictly before the given
     * date.
     *
     * @param date the date in the format "DD-MM-YYYY"
     * @return the names of all files that were created before the given date. If there are no files
     * that were created before the given date, return the empty set.
     */
    public Set<String> getFilesBeforeDate(String date) {
        Set<String> dateSet = new HashSet<>();

        if (date == null || date.isEmpty()) {
            return dateSet;
        }

        for (File file : fileSystem.values()) {
            if (file.fileDate != null && beforeGivenDate(date, file.fileDate)) {
                dateSet.add(file.fileName);
            }
        }
        return dateSet;
    }

    public static boolean beforeGivenDate(String date, String fileDate) {
        String[] part1 = date.split("-");
        String[] part2 = fileDate.split("-");

        int day1 = Integer.parseInt(part1[0]);
        int month1 = Integer.parseInt((part1[1]));
        int year1 = Integer.parseInt(part1[2]);

        int day2 = Integer.parseInt(part2[0]);
        int month2 = Integer.parseInt(part2[1]);
        int year2 = Integer.parseInt(part2[2]);

        if (year1 > year2) {
            return true;
        }
        if (year1 < year2) {
            return false;
        }
        if (month1 > month2) {
            return true;
        }
        if (month1 < month2) {
            return false;
        }
        return day1 > day2;
    }

    /**
     * Gets the maximum number of unique users and owners across any single date. If a user owns or
     * has permission to access multiple files that were created on the same date, this only adds
     * one towards the total number of users.
     *
     * @return the maximum number of users for any single date.
     */
    public int getMaxUsers() {
        if (fileSystem == null || fileSystem.isEmpty()) {
            return 0;
        }

        Set<String> dateSet = new HashSet<>();
        for (File file : fileSystem.values()) {
            dateSet.add(file.fileDate);
        }

        int maxNum = 0;
        // 对每个日期，计算该日期涉及的所有用户
        for (String date : dateSet) {
            Set<String> usersOnThisDate = new HashSet<>();

            for (File file : fileSystem.values()) {
                if (date.equals(file.fileDate)) {
                    usersOnThisDate.add(file.fileOwner);
                    usersOnThisDate.addAll(file.filePermissions);
                }
            }
            // 内层循环结束，当前日期user统计完成，进行数量比较
            if (maxNum < usersOnThisDate.size()) {
                maxNum = usersOnThisDate.size();
            }
        }
        return maxNum;
    }
}

