import java.util.Scanner;
import java.util.Random;

public class GuessNumber {
    private static final int EASY_UPPER_BOUND = 10;
    private static final int MEDIUM_UPPER_BOUND = 50;
    private static final int HARD_UPPER_BOUND = 100;
    private static final int DEFAULT_ATTEMPTS = 3;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        while (true) {
            System.out.println("1. Start Game");
            System.out.println("0. Exit");
            System.out.print("\nEnter number: ");
            String input = scanner.nextLine();

            if (input.equals("1")) {
                startGame(scanner, random);
            } else if (input.equals("0")) {
                System.out.println("Thank you for playing! Goodbye!");
                break;
            } else {
                System.out.println("Invalid input. Please enter 1 to start or 0 to exit.");
            }
        }
        scanner.close();
    }

    private static void startGame(Scanner scanner, Random random) {
        System.out.println("\nSelect Difficulty");
        System.out.println("1. Easy (0-9)");
        System.out.println("2. Medium (0-49)");
        System.out.println("3. Hard (0-99)");
        System.out.println("4. Custom");
        System.out.print("\nEnter number: ");
        
        String inputDifficulty = scanner.nextLine();
        int lowerBound = 0;
        int upperBound = 0;

        switch (inputDifficulty) {
            case "1": 
                lowerBound = 0; 
                upperBound = EASY_UPPER_BOUND; 
                break;
            case "2": 
                lowerBound = 0; 
                upperBound = MEDIUM_UPPER_BOUND; 
                break;
            case "3": 
                lowerBound = 0; 
                upperBound = HARD_UPPER_BOUND; 
                break;
            case "4":
                try {
                    System.out.print("Enter the minimum value: ");
                    lowerBound = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter the maximum value: ");
                    upperBound = Integer.parseInt(scanner.nextLine());

                    if (lowerBound >= upperBound) {
                        System.out.println("Invalid range. Minimum must be less than maximum.");
                        return;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter valid numbers for the range.");
                    return;
                }
                break;
            default:
                System.out.println("Invalid difficulty selection.");
                return;
        }

        int numberToGuess = random.nextInt(upperBound - lowerBound) + lowerBound;
        boolean hasGuessedCorrectly = false;
        int attemptsLeft = DEFAULT_ATTEMPTS;

        while (!hasGuessedCorrectly && attemptsLeft > 0) {
            System.out.printf("\nGuess the number (%d to %d) (%d attempts left): ", lowerBound, upperBound - 1, attemptsLeft);
            
            int userGuess;
            try {
                userGuess = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            if (userGuess == numberToGuess) {
                System.out.println("\nYou win!");
                hasGuessedCorrectly = true;
            } else {
                attemptsLeft--;
                if (attemptsLeft > 0) {
                    System.out.println("\nWrong guess! Try again.");
                } else {
                    System.out.printf("\nYou lose... The valid number was: %d\n", numberToGuess);
                }
            }
                    }

        playAgain(scanner, random);
    }

    private static void playAgain(Scanner scanner, Random random) {
        while (true) {
            System.out.print("\nDo you want to play again? (yes/no): ");
            String playAgainResponse = scanner.nextLine().trim().toLowerCase();

            if (playAgainResponse.equals("yes")) {
                startGame(scanner, random);
                break;
            } else if (playAgainResponse.equals("no")) {
                break;
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
        }
    }
}
