import java.util.Scanner;

public class AreaCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            try {
                displayMenu();
                String inputString = scanner.nextLine();
                int input = Integer.parseInt(inputString);

                switch (input) {
                    case 1:
                        calculateCircleArea(scanner);
                        break;
                    case 2:
                        calculateRectangleArea(scanner);
                        break;
                    case 3:
                        calculateTriangleArea(scanner);
                        break;
                    case 0:
                        System.out.println("Exiting the program.");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid input. Please try again.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("1. Circle");
        System.out.println("2. Rectangle");
        System.out.println("3. Triangle");
        System.out.println("0. Exit");
        System.out.print("Enter number: ");
    }

    private static void calculateCircleArea(Scanner scanner) {
        double radius = getPositiveDouble(scanner, "Enter the radius of the circle: ");
        double area = Math.PI * radius * radius;
        System.out.printf("The area of the circle: %.2f%n", area);
    }

    private static void calculateRectangleArea(Scanner scanner) {
        double width = getPositiveDouble(scanner, "Enter the width of the rectangle: ");
        double height = getPositiveDouble(scanner, "Enter the height of the rectangle: ");
        double area = width * height;
        System.out.printf("The area of the rectangle: %.2f%n", area);
    }

    private static void calculateTriangleArea(Scanner scanner) {
        double base = getPositiveDouble(scanner, "Enter the base of the triangle: ");
        double height = getPositiveDouble(scanner, "Enter the height of the triangle: ");
        double area = (base * height) / 2;
        System.out.printf("The area of the triangle: %.2f%n", area);
    }

    private static double getPositiveDouble(Scanner scanner, String message) {
        double value = -1;
        while (value < 0) {
            System.out.print(message);
            try {
                value = Double.parseDouble(scanner.nextLine());
                if (value < 0) {
                    System.out.println("Value cannot be negative. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return value;
    }
}