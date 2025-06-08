package ws11b;

public class Deadlock {

    private static final Object A = new Object();
    private static final Object B = new Object();

    private static void thread1() {
        try {
            System.out.println("T1 started");
            synchronized (A) {
                System.out.println("T1 locked A");
                Thread.sleep(1000);
                System.out.println("T1 slept");
                synchronized (B) {
                    System.out.println("T1 locked B");
                    System.out.println("A!");
                }
            }
        } catch(InterruptedException e) {
            System.out.println("T1 interrupted");
        }
    }

    private static void thread2() {
        try {
            System.out.println("T2 started");
            synchronized (B) {
                System.out.println("T2 locked B");
                Thread.sleep(1000);
                System.out.println("T2 slept");
                synchronized (A) {
                    System.out.println("T2 locked A");
                    System.out.println("B!");
                }
            }
        } catch(InterruptedException e) {
            System.out.println("T2 interrupted");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t=new Thread(Deadlock::thread2);
        t.start();

        thread1();

        t.join();
    }

}
