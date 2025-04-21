package p3;

public class Word {

    private String word;
    private int count;

    public Word(String word, int count) {
        // 参数验证
        if (word == null) {
            throw new IllegalArgumentException("Word cannot be null");
        }
        if (count < 0) {
            throw new IllegalArgumentException("Count must be non-negative");
        }

        this.word = word;
        this.count = count;
    }

    String getWord() {
        return word;
    }

    int getCount() {
        return count;
    }
}
