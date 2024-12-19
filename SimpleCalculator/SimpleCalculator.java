import java.util.Scanner;

public class SimpleCalculator  {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double firstNumber;
        double secondNumber;
        double calculationResult;
        String operation;

        System.out.print("Enter the first number: ");
        while (!scanner.hasNextDouble()) {
            System.out.println("Please enter a valid number.");
            scanner.next();
        }
        firstNumber = scanner.nextDouble();

        System.out.print("Enter the second number: ");
        while (!scanner.hasNextDouble()) {
            System.out.println("Please enter a valid number.");
            scanner.next();
        }
        secondNumber = scanner.nextDouble();

        System.out.print("Choose an operation (+, -, *, /): ");
        operation = scanner.next();

        switch (operation) {
            case "+":
                calculationResult = firstNumber + secondNumber;
                break;
            case "-":
                calculationResult = firstNumber - secondNumber;
                break;
            case "*":
                calculationResult = firstNumber * secondNumber;
                break;
            case "/":
                if (secondNumber == 0) {
                    System.out.println("Division by zero is not allowed.");
                    scanner.close();
                    return;
                }
                calculationResult = firstNumber / secondNumber;
                break;
            default:
                System.out.println("Invalid operation.");
                scanner.close();
                return;
        }

        System.out.printf("Result: %.2f\n", calculationResult);
        scanner.close();
    }
}
