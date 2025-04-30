package parallel_distributing;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Menu class is the main class that holds the links to the other classes. It contains two methods, main() and
 * menu().
 */
public class Menu {

    /**
     * Method calls two functions, menu() and InputHandler.close(). The call to menu brings up the menu with all
     * the different types of solvers which the user can select. InputHandler.close() closes the scanner before the
     * program ends. It throws two exceptions, InterruptedException and ExecutionException. InterruptedException is
     * thrown from either the UnboundedSolver or ExecutorSolver. ExecutionException is throw from the ExecutorSolver.
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
        menu();

        InputHandler.close();
    }

    /**
     * Method prints the solver menu for the user to select from. Method calls the inputHandler() function to verify
     * user input. Once the user input is verified, it will call the selected solver. It will print out the number
     * of primes and how long it took the solver to run. Solver will continue to run until the user inputs 0.
     */
    protected static void menu() throws InterruptedException, ExecutionException, IOException {
        System.out.println("""
                Hello, Please Pick a Menu Option:
                1) Single Threaded Solver
                2) Unbounded Solver
                3) Executor Solver
                4) Stream Solver
                5) Distributed Solver
                0) Quit""");

        int menuInput = InputHandler.inputHandler("menu");


        boolean repeat = true;
        switch (menuInput) {
            case 0:
                break;

            case 1:
                while (repeat) {
                    System.out.println("""
                            \n
                            Welcome to the Single Threaded Sieve of Eratosthenes Solver!
                            Please enter a number and we will return the amount of prime numbers between 1 and that number (inclusive).
                            Enter 0 to return to the main menu.""");

                    int solverInput = InputHandler.inputHandler("solver");

                    if (solverInput != 0) {
                        long before = System.nanoTime();
                        int count = SingleThreadedSolver.solver(solverInput);
                        long time = System.nanoTime() - before;

                        System.out.println("\n" + count + " primes");
                        System.out.println(time + " nanoseconds");
                    }
                    else {
                        repeat = false;
                    }
                }
                break;

            case 2:
                while (repeat) {
                    System.out.println("""
                            \n
                            Welcome to the Unbounded Sieve of Eratosthenes Solver!
                            Please enter a number and we will return the amount of prime numbers between 1 and that number (inclusive).
                            Enter 0 to return to the main menu.""");

                    int solverInput = InputHandler.inputHandler("solver");

                    if (solverInput != 0) {
                        long before = System.nanoTime();
                        int count = UnboundedSolver.threadMaker(solverInput);
                        long time = System.nanoTime() - before;

                        System.out.println("\n" + count + " primes");
                        System.out.println(time + " nanoseconds");
                    }
                    else {
                        repeat = false;
                    }
                }
                break;

            case 3:
                while (repeat) {
                    System.out.println("""
                            \n
                            Welcome to the Executor Sieve of Eratosthenes Solver!
                            Please enter a number and we will return the amount of prime numbers between 1 and that number (inclusive).
                            Enter 0 to return to the main menu.""");

                    int solverInput = InputHandler.inputHandler("solver");

                    if (solverInput != 0) {
                        long before = System.nanoTime();
                        int count = ExecutorSolver.solver(solverInput);
                        long time = System.nanoTime() - before;

                        System.out.println("\n" + count + " primes");
                        System.out.println(time + " nanoseconds");
                    }
                    else {
                        repeat = false;
                    }
                }
                break;

            case 4:
                while (repeat) {
                    System.out.println("""
                            \n
                            Welcome to the Stream Sieve of Eratosthenes Solver!
                            Please enter a number and we will return the amount of prime numbers between 1 and that number (inclusive).
                            Enter 0 to return to the main menu.""");

                    int solverInput = InputHandler.inputHandler("solver");

                    if (solverInput != 0) {
                        long before = System.nanoTime();
                        int count = StreamSolver.solver(solverInput);
                        long time = System.nanoTime() - before;

                        System.out.println("\n" + count + " primes");
                        System.out.println(time + " nanoseconds");
                    }
                    else {
                        repeat = false;
                    }
                }
                break;

            case 5:
                while (repeat) {
                    System.out.println("""
                            \n
                            Welcome to the Distributed Sieve of Eratosthenes Solver!
                            Please enter a number and we will return the amount of prime numbers between 1 and that number (inclusive).
                            Enter 0 to return to the main menu.""");

                    int solverInput = InputHandler.inputHandler("solver");

                    if (solverInput != 0) {
                        long before = System.nanoTime();
                        int count = DistributedSolver.distribute(solverInput);
                        long time = System.nanoTime() - before;

                        System.out.println("\n" + count + " primes");
                        System.out.println(time + " nanoseconds");
                    }
                    else {
                        repeat = false;
                    }
                }
                break;
        }
    }
}