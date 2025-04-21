package p3;

public class Ancestor {
    String name;
    int distance;

    public Ancestor(String name, int distance) {
        this.name = name;
        this.distance = distance;
    }

    public String name() {
        return this.name;
    }

    public int distance() {
        return this.distance;
    }
}
