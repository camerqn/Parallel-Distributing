package parallel_distributed_assignment;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ExecutorSolver class performs the Sieve of Eratosthenes algorithm by using the Executor framework to create threads.
 * It contains the method solver().
 */
public class ExecutorSolver {
    /**
     * Method will create a Boolean array that will be shared amongst threads. The method will then use ExecutorService
     * and Future of the Executor Framework in order to create threads. A thread pool is created with the number of
     * threads being equal to the number of available processors + 1 and Futures makes an array for each thread. The
     * method will then create chunks for each thread, stating its start and end boundaries in the shared Boolean
     * array allowing for thread safety. Executor then creates a thread and however many primes are counted in that
     * chunk will be added to the Atomic Integer count. After all futures have completed, the executor shuts down and
     * count is returned.s
     *
     *
     * @param input the maximum number to check for primes
     * @return the amount of primes between 0 and the input
     */
    protected static int solver(int input) throws ExecutionException, InterruptedException {
        final AtomicInteger count = new AtomicInteger(0);
        final int threadCount = Runtime.getRuntime().availableProcessors() + 1;

        Boolean[] prime = new Boolean[input + 1];
        for (int x = 2; x <= input; x++) {
            prime[x] = true;
        }

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        Future<?>[] futures = new Future[threadCount];

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

            futures[x] = executor.submit(() -> {
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
                count.addAndGet(countForThread);
            });
        }

        for (Future<?> future : futures) {
            if (future != null) {
                future.get();
            }
        }
        executor.shutdown();

        return count.get();
    }
}
