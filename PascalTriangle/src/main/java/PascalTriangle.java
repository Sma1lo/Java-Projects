import java.util.Scanner;

public class PascalTriangle {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.print("Enter number of rows of Pascal's Triangle or 'q' to quit: ");
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("q")) {
                    System.out.println("Exiting...");
                    break;
                }

                int rows = Integer.parseInt(input);
                int[][] triangle = new int[rows][];

                for (int i = 0; i < rows; i++) {
                    triangle[i] = new int[i + 1];
                    triangle[i][0] = triangle[i][i] = 1;

                    for (int j = 1; j < i; j++) {
                        triangle[i][j] = triangle[i - 1][j - 1] + triangle[i - 1][j];
                    }

                    for (int j = 0; j < rows - i - 1; j++) {
                        System.out.print(" ");
                    }

                    for (int num : triangle[i]) {
                        System.out.print(num + " ");
                    }
                    System.out.println();
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input, enter a valid number or 'q' to quit.");
            }
        }

        scanner.close();
    }
}
