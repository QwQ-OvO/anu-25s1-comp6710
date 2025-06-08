package ws11b;

public class SynchronizedSummers implements Runnable {

    private long singleSum;
    private long sharedSum;

    private void incSingleSum(long value) {
        singleSum+=value;
    }

    private void sumSingle() {
        for(long i=0;i<50000;i++) {
            incSingleSum(i*2);
        }
    }

    // Only one thread can enter this method at the same time
    private synchronized void incSharedSum(long value) {
        sharedSum+=value;
    }

    public void run() {
        for(long i=0;i<50000;i++) {
            incSharedSum(i);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SynchronizedSummers s=new SynchronizedSummers();

        s.sumSingle();
        System.out.println(s.singleSum);

        // Create new thread that will run sumShared
        Thread t=new Thread(s);
        // start the thread
        t.start();

        //run sumShared in this thread, too
        s.run();

        //wait for other thread to finish
        t.join();

        System.out.println(s.sharedSum);
    }
}
