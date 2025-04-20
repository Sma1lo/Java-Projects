import java.util.Scanner;
import java.util.Random;

public class GuessTheRoll {

    public static void main(String[] args) {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            MainMenu();

            try {
                System.out.print("\nEnter a number from 1 to 6: ");
                int input = scanner.nextInt();

                if (input < 1 || input > 6) {
                    System.out.println("Please enter a number between 1 and 6.");
                    continue;
                }

                int first_bone = random.nextInt(6) + 1;
                int second_bone = random.nextInt(6) + 1;
                int third_bone = random.nextInt(6) + 1;

                System.out.println("\nDice rolled:");
                System.out.println("First die:  " + first_bone);
                System.out.println("Second die: " + second_bone);
                System.out.println("Third die:  " + third_bone);

                if (input == first_bone || input == second_bone || input == third_bone) {
                    System.out.println(ConsoleColor.GREEN + "\n>>> YOU WIN! <<<\n" + ConsoleColor.RESET);
                } else {
                    System.out.println(ConsoleColor.RED + "\n>>> YOU LOSE... <<<\n" + ConsoleColor.RESET);
                }

            } catch (Exception e) {
                System.out.println(ConsoleColor.RED + "\nInvalid input. Please enter a valid number.\n" + ConsoleColor.RESET);
                scanner.nextLine();
            }
        }
    }

    private static void MainMenu() {
        System.out.println(ConsoleColor.GREEN + "\n===============================" + ConsoleColor.RESET);
        System.out.println(ConsoleColor.YELLOW + "        Guess the Roll" + ConsoleColor.RESET);
        System.out.println(ConsoleColor.GREEN + "===============================\n" + ConsoleColor.RESET);
    }
}
