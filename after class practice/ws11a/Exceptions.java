package ws11a;

import java.io.IOException;

public class Exceptions {

    public static void main(String[] args) {
        try {
            Foo();
        } catch(IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            System.out.println("Finally!");
        }
    }

    interface MyIOFun<T,R> {
        R apply(T t) throws IOException;
    }

    public static void Foo() throws IOException {
        try {
            MyIOFun<Integer, Integer> fun = i -> {
                if (i == 0) {
                    throw new IOException("Bad i!");
                }
                return i;
            };

            System.out.println(Bar(fun, 5));
            System.out.println(Bar(fun, 0));
            throw new IOException();
        } catch(IOException e) {
            System.out.println(e+" in Foo!");
        }
    }

    public static Integer Bar(MyIOFun<Integer, Integer> fun, int i) {
        try {
            return fun.apply(i);
        } catch(Exception e) {
            System.out.println(e+" in Bar!");
        }
        return 0;
    }


}

