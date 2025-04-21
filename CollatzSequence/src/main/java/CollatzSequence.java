import java.util.Scanner;

/**
 *
 *@author Sma1lo
 */

public class CollatzSequence {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number: ");
        try {
            int input = scanner.nextInt();

            if (input <= 0) {
                System.out.println("Please enter a positive integer.");
                return;
            }

            int steps = 0;
            System.out.println("Collatz sequence:");
            while (input != 1) {
                System.out.print(input + " ");
                if (input % 2 == 0) {
                    input = input / 2;
                } else {
                    input = input * 3 + 1;
                }
                steps++;
            }
            System.out.println(1);
            steps++;

            System.out.println("Total steps: " + steps);
        }
        catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }
}
