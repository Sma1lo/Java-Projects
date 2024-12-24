import java.util.Scanner;

public class ArithmeticMeanCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of values: ");
        int count = scanner.nextInt();

        if (count <= 0) {
            System.out.println("The number of values must be greater than zero.");
            scanner.close();
            return;
        }

        double sum = 0;

        for (int i = 1; i <= count; i++) {
            System.out.print("Enter number " + i + ": ");
            double number = scanner.nextDouble();
            sum += number;
        }

        double average = sum / count;
        System.out.printf("Arithmetic mean: %.2f%n", average);

        scanner.close();
    }
}
