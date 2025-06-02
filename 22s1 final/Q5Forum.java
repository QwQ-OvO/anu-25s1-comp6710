package comp1110.exam;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This class represents a simplified forum. Each page on the forum has a name, the date it was
 * last modified, an admin person and a set of moderator people. Each page is uniquely identified by
 * its name. People may be both admins and moderators.
 */
public class Q5Forum {

    //内部类 Forum 代表单个论坛页面，需要具体的数据来初始化
    public static class Forum {
        String name;
        String date;
        String admin;
        Set<String> moderatorPeople;

        public Forum(String name, String date, String admin, Set<String> moderatorPeople) {
            this.name = name;
            this.date = date;
            this.admin = admin;
            this.moderatorPeople = moderatorPeople;
        }
    }

    //外部类 Q5Forum 代表整个论坛系统，初始时应该是空的，等待后续添加论坛页面
    //这样设计才符合"论坛系统包含多个论坛页面"的逻辑关系
    public Map<String, Forum> forumSystem;
    public Q5Forum() {
        this.forumSystem = new HashMap<>();
    }

    /**
     * Add a page to the forum. If the page name already exists, do not modify the forum.
     *
     * @param name   the name of the page.
     * @param admin  the name of the administrator for this page.
     * @param date   the date this page was last modified in the format "YYYY-MM-DD"
     * @param moderators  the names of moderators of this page.
     * @return true if the page was added, false if the page was not added (because a page with that
     * name already exists).
     */
    public boolean addPage(String name, String admin, String date, Set<String> moderators) {
        if (name == null || name.isEmpty()) {
            return false;
        }
        if (!forumSystem.containsKey(name)) {
            Forum value = new Forum(name, date, admin, moderators); // 严格对应构造函数顺序穿参
            forumSystem.put(name, value);
            return true;
        }
        return false;
    }

    /**
     * Remove the page with the given name from this forum. If no page with the given name
     * exists, do not modify this forum.
     *
     * @param name the name of the page to be removed.
     * @return true if removal was successful, otherwise false.
     */
    public boolean deletePage(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }
        if (!forumSystem.containsKey(name)) {
            return false;
        }
        forumSystem.remove(name);
        return true;
    }

    /**
     * @return the total number of pages in this forum.
     */
    public int getPageCount() {
        return forumSystem.size();
    }

    /**
     * Get the names of all pages that have the given admin. If the given name is not an
     * admin for any files, return an empty set.
     *
     * @param admin the admin to search for.
     * @return a set of all names of pages that have this admin.
     */
    public Set<String> getPagesForAdmin(String admin) {
        Set<String> pageName = new HashSet<>();
        if (admin == null || admin.isEmpty()) {
            return pageName;
        }

        for (Forum value : forumSystem.values()) {
            if (value.admin.equals(admin)) { //使用 equals() 进行精确匹配
                pageName.add(value.name);
            }
        }
        return pageName;
    }

    /**
     * Get the names of all pages in which the given person is either an admin or moderator. If
     * the given person is not either of these, return an empty set.
     *
     * @param name the name of a person.
     * @return a set of names of all pages that this person is either an admin or a moderator. If
     * this person is not an admin or moderator for any page, return an empty set.
     */
    public Set<String> getPages(String name) {
        Set<String> pageName = new HashSet<>();
        if (name == null || name.isEmpty()) {
            return pageName;
        }

        for (Forum value : forumSystem.values()) {
            if (value.admin.equals(name) || value.moderatorPeople.contains(name)) {
                pageName.add(value.name); //value.name（Forum对象的属性）= 页面的名字
            }
        }
        return pageName;
    }

    /**
     * Get the total number of unique people across all pages in this forum. Each person is only
     * counted once even if they are both an admin and a moderator or if they are listed for
     * several pages.
     *
     * @return the number of unique people across all pages.
     */
    public int getNumPeople() {
        Set<String> uniqueName = new HashSet<>();

        for (Forum value : forumSystem.values()) {
            uniqueName.add(value.admin);
            uniqueName.addAll(value.moderatorPeople);
        }
        return  uniqueName.size();
    }

    /**
     * Get the greatest number of pages that a person is an admin or moderator for. If a given
     * person is both an admin and a moderator for a page, that page only counts once.
     *
     * @return the maximum number of pages for any person
     */
    public int getMaxPages() {Set<String> pageName = new HashSet<>();

        Set<String> personSet = new HashSet<>();
        int maxNum = 0;

        for (Forum value : forumSystem.values()) {

            personSet.add(value.admin);
            personSet.addAll(value.moderatorPeople);

            for (String s : personSet) {
                int count = getPages(s).size();
                if (count > maxNum) {
                    maxNum = count;
                }
            }
        }
        return maxNum;
    }

    /**
     * Get the names of all pages in the forum that were modified after the given date.
     *
     * @param date the date in the format "YYYY-MM-DD"
     * @return a set of names of all pages that were modified after the given date. If there are no
     * pages that were modified after the date, return an empty set.
     */
    public Set<String> getPagesAfterDate(String date) {
        Set<String> dateSet = new HashSet<>();

        if (date == null || date.isEmpty()) {
            return dateSet;
        }

        for (Forum value : forumSystem.values()) {
            String date1 = date;
            String date2 = value.date;
            if (isModifiedBefore(date1, date2)) {
                dateSet.add(value.name);
            }
        }
        return dateSet;
    }

    private static boolean isModifiedBefore(String date1, String date2) {
        String[] part1 = date1.split("-");
        String[] part2 = date2.split("-");

        int year1 = Integer.parseInt(part1[0]);
        int year2 = Integer.parseInt(part2[0]);
        int month1 = Integer.parseInt(part1[1]);
        int month2 = Integer.parseInt(part2[1]);
        int day1 = Integer.parseInt(part1[2]);
        int day2 = Integer.parseInt(part2[2]);

        if (year1 > year2) {
            return false;
        }
        if (year1 < year2) {
            return  true;
        }
        if (month1 > month2) {
            return false;
        }
        if (month1< month2) {
            return true;
        }
        return day1 < day2;
    }

    /**
     * Get the maximum number of unique admins and moderators across any single date. If a person
     * is an admin or moderator for multiple pages that were modified on the same date, this only
     * adds one towards the total number of people.
     *
     * @return the maximum number of people for any single date.
     */
    public int getMaxPeople() {
        Set<String> oneDate = new HashSet<>();
        int maxPeople = 0;
        for (Forum value : forumSystem.values()) {
            oneDate.add(value.date);
            

            for (String date : oneDate) {
                Set<String> dateName = new HashSet<>();

                for (Forum forum : forumSystem.values()) {
                    if (date.equals(forum.date)) {
                        dateName.add(forum.admin);
                        dateName.addAll(forum.moderatorPeople);
                    }
                }
                int count = dateName.size();
                if (maxPeople < count) {
                    maxPeople = count;
                }
            }
        }
        return maxPeople;
    }
}
