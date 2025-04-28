package parallel_distributed_assignment;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * DistributedSolver class performs the Sieve of Eratosthenes algorithm by using distributed systems. It contains the
 * method distribute which divides and passes half the work to the distributed system and the other half on the local
 * machine. It also contains the method solver() which is the same as StreamSolver.
 */
public class DistributedSolver {
    public DistributedSolver() throws IOException, InterruptedException {
    }

    /**
     * Method will divide the work in half and send half to the distributed system and the other half would be done
     * locally through the solver method. This method will create a client, request, and response from the HttpClient
     * import. The distributed system, which runs on a virtual machine will receive the request and perform the work
     * and then return a count of all the primes in its range. The method uses Futures to synchronize the prime counts
     * that are done local and from the distributed system. This count will get added to the variable count. The
     * solver will use parallel streams to get the prime count in its range. This count will also get added to the
     * variable count. Count will then be returned with all the primes count between 0 and the input.
     *
     * @param input the maximum number to check for primes
     * @return the amount of primes between 0 and the input
     */
    protected static int distribute(int input) throws InterruptedException, ExecutionException {
        final AtomicInteger count = new AtomicInteger(0);
        int split = (input/2);

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://127.0.0.1:5000/countPrimes/" + split))
                .build();

        CompletableFuture<HttpResponse<String>> distributedFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return client.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        CompletableFuture<Integer> localFuture = CompletableFuture.supplyAsync(() -> solver(split + 1, input));

        count.addAndGet(Integer.parseInt(distributedFuture.get().body()));
        count.addAndGet(localFuture.get());

        return count.get();
    }

    /**
     * Method will create a Boolean array, all set to true, that will be shared amongst parallel streams. The method
     * will then create a stream with a closed range from 2 to the square root of the input. It then calls .parallel()
     * which makes the stream parallel. The stream will then go through each number in the range and set all of its
     * multiples to false. Another parallel stream is created and goes through the array to count all the trues.
     * This stream is set equal to a variable called count. The variable count is then returned.
     *
     * @param start the starting position in the boolean array
     * @param end the ending position in the boolean array
     * @return the amount of primes between start and end
     */
    protected static int solver(int start, int end) {
        Boolean[] prime = new Boolean[end + 1];
        Arrays.fill(prime, start, end + 1, true);

        IntStream.rangeClosed(start, (int) Math.sqrt(end))
                .parallel()
                .filter(i -> prime[i])
                .forEach(x -> {
                    for (int z = x * x; z <= end; z += x) {
                        prime[z] = false;
                    }
                });

        long primeCount = IntStream.rangeClosed(start, end)
                .parallel()
                .filter(x -> prime[x])
                .count();

        return Math.toIntExact(primeCount);
    }
}
