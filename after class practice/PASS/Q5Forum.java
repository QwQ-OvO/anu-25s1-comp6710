package PASS;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a simplified forum. Each page on the forum has a name, the date it was
 * last modified, an admin person and a set of moderator people. Each page is uniquely identified by
 * its name. People may be both admins and moderators.
 */
public class Q5Forum {

    public class Page {
        String name;
        String admin;
        String date;
        Set<String> moderators;

        public Page(String name, String admin, String date, Set<String> moderators) {
            this.name = name;
            this.admin = admin;
            this.date = date;
            this.moderators = moderators;
        }
    }
    HashMap<String, Page> forum;
    public Q5Forum() {
        forum = new HashMap<>();
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
        if (forum.containsKey(name)) {
            return false;
        } else {
            Page newPage = new Page(name, admin, date, moderators);
            forum.put(name, newPage);
        }
        return true;
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
        if (!forum.containsKey(name)) {
            return  false;
        } else {
            forum.remove(name);
        }
        return true;
    }

    /**
     * @return the total number of pages in this forum.
     */
    public int getPageCount() {
        return forum.size();
    }

    /**
     * Get the names of all pages that have the given admin. If the given name is not an
     * admin for any files, return an empty set.
     *
     * @param admin the admin to search for.
     * @return a set of all names of pages that have this admin.
     */
    public Set<String> getPagesForAdmin(String admin) {
        Set<String> givenName = new HashSet<>();
        if (admin == null || admin.isEmpty()) {
            return givenName;
        } else {
            for (Page value : forum.values()) {
                if (value.admin.equals(admin)) {
                    givenName.add(value.name);
                }
            }
        }
        return givenName;
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
        Set<String> nameSet = new HashSet<>();
        if (name == null || name.isEmpty()) {
            return nameSet;
        }
        for (Page value : forum.values()) {

            //String.contains(String) 会检查一个字符串是否包含另一个字符串作为子串
            if (value.admin.equals(name)) {
                nameSet.add(value.name);
            }
            // Set<String>.contains(String) 会使用 equals 方法来比较集合中的每个元素与给定的字符串
            if (value.moderators.contains(name)) {
                nameSet.add(value.name);
            }
        }
        return nameSet;
    }

    /**
     * Get the total number of unique people across all pages in this forum. Each person is only
     * counted once even if they are both an admin and a moderator or if they are listed for
     * several pages.
     *
     * @return the number of unique people across all pages.
     */
    public int getNumPeople() {
        return -1; // FIXME complete this method
    }

    /**
     * Get the greatest number of pages that a person is an admin or moderator for. If a given
     * person is both an admin and a moderator for a page, that page only counts once.
     *
     * @return the maximum number of pages for any person
     */
    public int getMaxPages() {
        return -1; // FIXME complete this method
    }

    /**
     * Get the names of all pages in the forum that were modified after the given date.
     *
     * @param date the date in the format "YYYY-MM-DD"
     * @return a set of names of all pages that were modified after the given date. If there are no
     * pages that were modified after the date, return an empty set.
     */
    public Set<String> getPagesAfterDate(String date) {
        return null; // FIXME complete this method
    }

    /**
     * Get the maximum number of unique admins and moderators across any single date. If a person
     * is an admin or moderator for multiple pages that were modified on the same date, this only
     * adds one towards the total number of people.
     *
     * @return the maximum number of people for any single date.
     */
    public int getMaxPeople() {
        return -1; // FIXME this method might be useful for the second part of Q5
    }
}

