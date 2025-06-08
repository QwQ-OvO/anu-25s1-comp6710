package ws11b;

import java.util.Iterator;

public class OnIterators {

    public static void main(String[] args) {

        boolean b=true;
        while(b) {
            b = b && false;
        }
    }

    class MyIterator implements Iterator<Void> {
        boolean b=true; //the relevant state outside of the while loop
        @Override
        public boolean hasNext() {
            return b; //the condition of the while loop
        }

        @Override
        public Void next() {
            b = b && false; //the body of the while loop
            return null;
        }
    }
}
