import java.util.Scanner;

public class Calculator {

    public static String calculate(double firstNumber, double secondNumber, String operation) {
        switch (operation) {
            case "+":
                return String.valueOf(firstNumber + secondNumber);
            case "-":
                return String.valueOf(firstNumber - secondNumber);
            case "*":
                return String.valueOf(firstNumber * secondNumber);
            case "/":
                if (secondNumber == 0) {
                    return "Error: Division by zero";
                } else {
                    return String.valueOf(firstNumber / secondNumber);
                }
            default:
                return "Invalid operation";
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.print("Enter the operation (+, -, *, /) or q to quit: ");
                String operation = scanner.nextLine();

                if (operation.equals("q")) {
                    System.out.println("Exiting...");
                    scanner.close();
                    break;
                }

                System.out.print("Enter the first number: ");
                double firstNumber = scanner.nextDouble();

                System.out.print("Enter the second number: ");
                double secondNumber = scanner.nextDouble();
                scanner.nextLine();

                String result = calculate(firstNumber, secondNumber, operation);
                System.out.println("Answer: " + result);

            } catch (Exception e) {
                System.out.println("Invalid input: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }
}