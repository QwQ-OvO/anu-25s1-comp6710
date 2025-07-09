package PASS.Q5FileSystem_Practice;

import java.io.File;
import java.util.List;

/**
 * Represents a node in the file system tree. The representation,
 * including any nodes reachable from a particular instance,
 * refers to a particular point in time - the time when the
 * instance was created. Any changes to the file system after
 * that point are not reflected in any operations on the
 * object or the tree of nodes rooted at it.
 * 
 * We distinguish three kinds of nodes:
 * - directories, which have child nodes for every file and directory
 *   contained in the directory in the file system that the node represents
 * - text files, which are files whose name ends with ".txt", and
 *   which are therefore assumed to contain text data
 * - non-text files - all files that are not text files
 * 
 * FsNodes should support the following default notion of equality:
 * - Directories are equal if their names are equal and
 *   all contained files and directories are equal
 * - Text files are equal if their names are equal and
 *   they contain the same text - that is, all their lines of text are equal
 * - Non-text files are equal if their names are equal
 */
public interface FsNode {
    
    /**
     * Creates a new FsNode tree rooted at the given path in the
     * file system.
     * Examples:
     * - given: "testFiles/A" expect: a directory node representing
     *   that directory and containing FsNodes representing the
     *   contents of that directory
     * - given: "testFiles/A/C.txt" expect: a text file node
     *   representing that file and its contents at the time the
     *   node was created
     * - given: "testFiles/A/D.doc" expect: a non-text-file node
     *   representing that file
     * @param file an abstract path to an actual file or directory
     *              in the file system
     * @return the root FsNode of a tree of such nodes representing
     *         the part of the file system rooted at the given path
     */
    static FsNode createNode(File file) {
        if (file.isDirectory()) {
            return new FsDir(file);
        }
        if (file.getName().endsWith(".txt")) {
            return new FsTextFile(file);
        } else {
            return new FsNonTextFile(file);
        }
    }
    
    /**
     * @return the abstract path of the file or directory
     * represented by the node
     */
    File getUnderlyingFile();
    
    /**
     * Lists all files and directories in the file system
     * structure rooted in the current node, represented as
     * FsNodes. If the node represents a file, the list
     * contains the current node as its single element.
     * If the node is a directory, the list should follow a
     * pre-order depth-first-search exploration of the file
     * system tree. Each directory should explore its children
     * in alphabetical order, given by the natural ordering
     * of Strings via String.compareTo
     * Examples:
     * - if this represents "testFiles/A" expect: a list of
     *   FsNodes representing, in order:
     *   ["testFiles/A", "testFiles/A/B", "testFiles/A/B/C.txt",
     *    "testFiles/A/C.txt", "testFiles/A/D.doc"]
     * @return a list representing the pre-order DFS
     *   traversal of the file system tree rooted at the
     *   current node
     */
    List<FsNode> allNodes();
} 