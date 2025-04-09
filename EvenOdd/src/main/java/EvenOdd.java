import java.util.Scanner;

public class EvenOdd {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.print("Enter number (or 'q' to quit): ");
            String input = scan.nextLine();

            if (input.equalsIgnoreCase("q")) {
                System.out.println("Exiting...");
                break;
            }

            try {
                int number = Integer.parseInt(input);
                if (number % 2 == 0) {
                    System.out.println("Answer: Even");
                } else {
                    System.out.println("Answer: Odd");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number or 'q' to quit.");
            }
        }
        scan.close();
    }
}