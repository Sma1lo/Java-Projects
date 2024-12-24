import java.util.Scanner;

public class AreaCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            displayMenu();
            choice = getUserChoice(scanner);

            switch (choice) {
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
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("Select a shape to calculate the area:");
        System.out.println("1. Circle");
        System.out.println("2. Rectangle");
        System.out.println("3. Triangle");
        System.out.println("0. Exit");
        System.out.print("\nEnter number: ");
    }

    private static int getUserChoice(Scanner scanner) {
        int choice = -1;
        while (true) {
            try {
                choice = scanner.nextInt();
                if (choice >= 0 && choice <= 3) {
                    break;
                } else {
                    System.out.println("Please enter a valid choice (0-3).");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
        return choice;
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
                value = scanner.nextDouble();
                if (value < 0) {
                    System.out.println("Value cannot be negative. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next();
            }
        }
        return value;
    }
          }
