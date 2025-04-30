package parallel_distributing;

import java.util.Arrays;
import java.util.stream.*;

/**
 * StreamSolver class performs the Sieve of Eratosthenes algorithm by using parallel streams. It contains the
 * method solver().
 */
public class StreamSolver {

    /**
     * Method will create a Boolean array, all set to true, that will be shared amongst parallel streams. The method
     * will then create a stream with a closed range from 2 to the square root of the input. It then calls .parallel()
     * which makes the stream parallel. The stream will then go through each number in the range and set all of its
     * multiples to false. Another parallel stream is created and goes through the array to count all the trues.
     * This stream is set equal to a variable called count. The variable count is then returned.
     *
     * @param input the maximum number to check for primes
     * @return the amount of primes between 0 and the input
     */
    protected static int solver(int input) {

        Boolean[] prime = new Boolean[input + 1];
        Arrays.fill(prime, 2, input + 1, true);

        IntStream.rangeClosed(2, (int) Math.sqrt(input))
                .parallel()
                .filter(i -> prime[i])
                .forEach(x -> {
                    for (int z = x * x; z <= input; z += x) {
                        prime[z] = false;
                    }
                    });

        long count = IntStream.rangeClosed(2, input)
                .parallel()
                .filter(x -> prime[x])
                .count();

        return Math.toIntExact(count);
    }
}
