package parallel_distributed_assignment;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

/**
 * InputHandler class handles the user's input and the scanner utility. It contains two methods, inputHandler() and
 * close().
 */
public class InputHandler {
    private static final Scanner scan = new Scanner(System.in);

    /**
     * Method checks user input depending on which mode it is in. If in menu mode, it will make sure the user inputs
     * an integer between 0 and 5. If in solver mode, it will make sure the user inputs a number greater than 0. If
     * 0 is submitted, it will call the menu() function.
     *
     * @param mode sets the mode to handle user input
     * @return the user's input
     */
    protected static int inputHandler(String mode) throws InterruptedException, ExecutionException, IOException {
        int input = -1;

        switch (mode) {
            case "menu":
                while (input < 0 || input > 5) {
                    if (scan.hasNextInt()) {
                        input = scan.nextInt();

                        if (input < 0 || input > 5) {
                            System.out.println("Please enter an integer between 0 and 5!");
                        }
                    } else {
                        System.out.println("Please enter an integer!");
                        scan.next();
                    }
                }

            case "solver":
                while (input < 0) {
                    if (scan.hasNextInt()) {
                        input = scan.nextInt();

                        if (input < 0) {
                            System.out.println("Please enter a non-negative integer!");
                        }

                        if (input == 0) {
                            Menu.menu();
                        }

                    }
                    else {
                        System.out.println("Please enter an integer!");
                        scan.next();
                    }
                }
        }
        return input;
    }

    /**
     * Method closes the scanner utility when the program is finished running.
     */
    protected static void close() {
        scan.close();
    }
}
