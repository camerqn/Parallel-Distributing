package parallel_distributing;

/**
 * SingleThreadedSolver class performs the Sieve of Eratosthenes algorithm in a single threaded manner. It contains
 * the method, solver().
 */
public class SingleThreadedSolver {
    /**
     * Method will calculate the number of prime integers between 0 and the input (inclusive) and will output
     * that number.
     *
     * @param input the maximum number to check for primes
     * @return the amount of primes between 0 and the input
     */
    protected static int solver(int input) {
        Boolean[] prime = new Boolean[input + 1];
        for (int x = 2; x <= input; x++) {
            prime[x] = true;
        }

        for (int i = 2; i <= Math.sqrt(input); i++) {
            if (prime[i]) {
                for (int j = i * i; j <= input; j += i) {
                    prime[j] = false;
                }
            }
        }

        int count = 0;
        for (int z = 2; z < input + 1; z++) {
            if (prime[z]) {
                count++;
            }
        }

        return count;
    }
}
