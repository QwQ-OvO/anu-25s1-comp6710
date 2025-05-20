package p3;

import java.io.File;

public class Ancestor {
    private String name;
    private int distance;
    private File file;

    public Ancestor(File file) {
        this.file = file;
        this.name = file.getName();
        this.distance = getDistance(file);
    }

    public String name() {
        return this.name;
    }

    public int distance() {
        return this.distance;
    }

    public static int getDistance(File file) {
        int currentDistance = 0;
        File currentFile = file;
        while (currentFile.getParentFile() != null) {
            currentDistance ++;
            currentFile = currentFile.getParentFile();
        }
        return currentDistance;
    }
}
