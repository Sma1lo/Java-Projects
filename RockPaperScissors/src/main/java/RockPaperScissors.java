import java.util.Scanner;
import java.util.Random;

public class RockPaperScissors {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        String[] options = {"rock", "paper", "scissors"};

        System.out.println(ConsoleColor.BLUE + "===" + ConsoleColor.RESET + " Welcome to Rock, Paper, Scissors " + ConsoleColor.BLUE + "===" + ConsoleColor.RESET);

        while (true) {
            System.out.println(ConsoleColor.BLUE + "\n[" + ConsoleColor.RESET + ConsoleColor.CYAN + "1" + ConsoleColor.RESET + ConsoleColor.BLUE + "]" + ConsoleColor.RESET + " Play");
            System.out.println(ConsoleColor.BLUE + "[" + ConsoleColor.RESET + ConsoleColor.CYAN + "0" + ConsoleColor.BLUE + "]" + ConsoleColor.RESET + " Exit");
            System.out.print("\nChoose an option: ");

            String choice = scanner.nextLine().trim();

            if (choice.equals("0")) {
                System.out.println(ConsoleColor.YELLOW + "Thanks for playing!" + ConsoleColor.RESET);
                break;
            } else if (!choice.equals("1")) {
                System.out.println(ConsoleColor.RED + "Invalid menu option. Try again." + ConsoleColor.RESET);
                continue;
            }

            System.out.print("Enter your move (Rock, Paper, Scissors): ");
            String playerMove = scanner.nextLine().trim().toLowerCase();

            if (!playerMove.equals("rock") && !playerMove.equals("paper") && !playerMove.equals("scissors")) {
                System.out.println(ConsoleColor.RED + "Invalid move! Choose Rock, Paper, or Scissors." + ConsoleColor.RESET);
                continue;
            }

            String computerMove = options[random.nextInt(3)];
            System.out.println("Computer chose: " + ConsoleColor.YELLOW + capitalize(computerMove) + ConsoleColor.RESET);

            if (playerMove.equals(computerMove)) {
                System.out.println(ConsoleColor.YELLOW + "It's a draw!" + ConsoleColor.RESET);
            } else if (
                    (playerMove.equals("rock") && computerMove.equals("scissors")) ||
                            (playerMove.equals("paper") && computerMove.equals("rock")) ||
                            (playerMove.equals("scissors") && computerMove.equals("paper"))
            ) {
                System.out.println(ConsoleColor.GREEN + "YOU WIN!" + ConsoleColor.RESET);
            } else {
                System.out.println(ConsoleColor.RED + "YOU LOSE..." + ConsoleColor.RESET);
            }
        }
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}