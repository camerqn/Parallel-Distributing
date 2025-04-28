package parallel_distributed_assignment;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.CountDownLatch;

/**
 * UnboundedSolver class performs the Sieve of Eratosthenes algorithm by using unbounded threads. It contains
 * the methods threadMaker() and solver().
 */
public class UnboundedSolver {

    /**
     * Method will create a Boolean array that will be shared amongst threads. The method will then create chunks
     * for each thread, stating its start and end boundaries in the shared Boolean array allowing for thread safety.
     * Threads will be created from the runnable task which calls solver using input, start, end, and the Boolean Array
     * as parameters. Each thread will get a primeCount which is then added to the count (the total count of primes)
     * which is an Atomic Integer. When a thread completes, it's Count Down Latch is decremented. When Count Down Latch
     * reaches 0, all threads have been completed. When this happens, the total count will be returned.
     *
     * @param input the maximum number to check for primes
     * @return the amount of primes between 0 and the input
     */
    protected static int threadMaker(final int input) throws InterruptedException {
        final AtomicInteger count = new AtomicInteger(0);
        final int threadCount = Runtime.getRuntime().availableProcessors() + 1;
        final CountDownLatch latch = new CountDownLatch(threadCount);

        Boolean[] prime = new Boolean[input + 1];
        for (int x = 2; x <= input; x++) {
            prime[x] = true;
        }

        for (int x = 0; x < threadCount; x++) {
            final int chunk = input / threadCount;
            final int start = (x * chunk) + 2;
            final int end;
            if (x == (threadCount - 1)) {
                end = input + 1;
            }
            else {
                end = start + chunk;
            }

            Runnable task = new Runnable() {
                public void run() {
                    int primeCount = solver(input, start, end, prime);
                    count.addAndGet(primeCount);
                    latch.countDown();
                }
            };

            new Thread(task).start();
        }

        latch.await();

        return count.get();
    }

    /**
     * Method will create a Boolean array that will be shared amongst threads. The method will then create chunks
     * for each thread, stating its start and end boundaries in the shared Boolean array allowing for thread safety.
     * Threads will be created from the runnable task which calls solver using input, start, end, and the Boolean Array
     * as parameters. Each thread will get a primeCount which is then added to the count (total) which is an
     * Atomic Integer. When a thread completes, it's Count Down Latch is decremented. When Count Down Latch reaches 0,
     * all threads have been completed. When this happens, the total count will be returned.
     *
     * @param input the maximum number to check for primes
     * @param start the starting point to count for primes
     * @param end the end point to count for primes
     * @param prime the shared Boolean Array
     * @return the amount of primes between the start and end points for this specific thread
     */
    protected static int solver(int input, int start, int end, Boolean[] prime) {
        for (int i = 2; i <= Math.sqrt(end); i++) {
            if (prime[i]) {
                for (int j = i * i; j <= input; j += i) {
                    prime[j] = false;
                }
            }
        }

        int countForThread = 0;

        for (int z = start; z < end; z++) {
            if (prime[z]) {
                countForThread++;
            }
        }


        return countForThread;
    }
}
