import java.util.Scanner;
import java.util.Random;

/**
 *
 *@author Sma1lo
 */

public class GuessNumber {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int wins = 0;
        int losses = 0;

        while (true) {
            try {
                mainMenu();
                int input = scanner.nextInt();

                if (input == 0) {
                    System.out.println("Exiting...");
                    break;
                }

                int max;
                int attempts;
                String difficultyText;
                String difficultyColor;

                switch (input) {
                    case 1:
                        max = 10;
                        attempts = 3;
                        difficultyText = "EASY (0 - 9), 3 tries";
                        difficultyColor = ConsoleColor.GREEN;
                        break;
                    case 2:
                        max = 25;
                        attempts = 4;
                        difficultyText = "MEDIUM (0 - 24), 4 tries";
                        difficultyColor = ConsoleColor.YELLOW;
                        break;
                    case 3:
                        max = 50;
                        attempts = 5;
                        difficultyText = "HARD (0 - 49), 5 tries";
                        difficultyColor = ConsoleColor.RED;
                        break;
                    default:
                        System.out.println("Invalid option. Try again.");
                        continue;
                }

                System.out.println("\nYou chose: " + difficultyColor + difficultyText + ConsoleColor.RESET + "\n");

                int target = random.nextInt(max);
                boolean won = false;

                for (int i = 0; i < attempts; i++) {
                    System.out.print("Guess a number (0 - " + (max - 1) + "), attempts left: " + (attempts - i) + ": ");
                    int guess = scanner.nextInt();

                    if (guess < 0 || guess >= max) {
                        System.out.println("Out of range! Try again.");
                        i--;
                        continue;
                    }

                    if (guess == target) {
                        System.out.println("YOU WIN!");
                        wins++;
                        won = true;
                        break;
                    } else if (guess < target) {
                        System.out.println("Too low!");
                    } else {
                        System.out.println("Too high!");
                    }
                }

                if (!won) {
                    System.out.println("YOU LOSE! The correct number was: " + target);
                    losses++;
                }

                System.out.println("Score: " + wins + " Wins / " + losses + " Losses\n");

            } catch (Exception e) {
                System.out.println("Invalid input. Try again.");
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    private static void mainMenu() {
        System.out.println(ConsoleColor.GREEN + "=====" + ConsoleColor.RESET + ConsoleColor.YELLOW + " GUESS THE NUMBER " + ConsoleColor.RESET + ConsoleColor.GREEN + "=====" + ConsoleColor.RESET);

        System.out.println(ConsoleColor.GREEN + "\t[" + ConsoleColor.YELLOW + "1" + ConsoleColor.GREEN + "]" + ConsoleColor.RESET + ConsoleColor.GREEN + " EASY" + ConsoleColor.RESET);
        System.out.println(ConsoleColor.GREEN + "\t[" + ConsoleColor.YELLOW + "2" + ConsoleColor.GREEN + "]" + ConsoleColor.RESET + ConsoleColor.YELLOW + " MEDIUM" + ConsoleColor.RESET);
        System.out.println(ConsoleColor.GREEN + "\t[" + ConsoleColor.YELLOW + "3" + ConsoleColor.GREEN + "]" + ConsoleColor.RESET + ConsoleColor.RED + " HARD" + ConsoleColor.RESET);
        System.out.println(ConsoleColor.GREEN + "\t[" + ConsoleColor.YELLOW + "0" + ConsoleColor.GREEN + "]" + ConsoleColor.RESET + " Exit");

        System.out.println(ConsoleColor.GREEN + "============================" + ConsoleColor.RESET);
        System.out.print("\nChoose difficulty: ");
    }
}