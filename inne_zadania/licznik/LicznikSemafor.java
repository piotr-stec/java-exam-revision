package inne_zadania.licznik;

import java.util.concurrent.Semaphore;

public class LicznikSemafor {
    private int count = 0;
    private final Semaphore semaphore = new Semaphore(1);

    public void increment() {
        try {
            semaphore.acquire();
            count++;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            semaphore.release();
        }
    }

    public void decrement() {
        try {
            semaphore.acquire();
            count--;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            semaphore.release();
        }
    }

    public int getCount() {
        try {
            semaphore.acquire();
            return count;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return count;
        } finally {
            semaphore.release();
        }
    }
}
