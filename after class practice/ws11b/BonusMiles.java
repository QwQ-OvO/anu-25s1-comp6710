package ws11b;

public class BonusMiles implements Runnable {
    private int[] transactions = new int[] { 5000, -8000, -1000 };
    private int balance = 10000;
    private int txNumber = 0;

    synchronized private void adjustBalance(int amount) {
        balance += amount;
    }

    private int nextTransactionNumber() {
        int txNum = txNumber;
        txNumber++;
        return txNum;
    }

    @Override
    public void run() {
        int currentTx = nextTransactionNumber();
        while(currentTx < transactions.length) {
            adjustBalance(transactions[currentTx]);
            currentTx = nextTransactionNumber();
        }
    }
}