package DFS;

import java.io.File;

public class FileSystemTraversal {

    public static void traverseDirectory(File directory, int level) {
        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            System.out.println(file.getName());

            if (file.isDirectory()) {
                traverseDirectory(file,level + 1);
            }
        }


    }
}
