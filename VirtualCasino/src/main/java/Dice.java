import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *@author Sma1lo
 */

public class Dice {
    public static void play() throws InterruptedException {
        Game.playTemplate("SIC BO", () -> {
            System.out.println("\nChoose bet type:");
            System.out.println(Casino.YELLOW + "1 - Big (11-17)" + Casino.RESET);
            System.out.println(Casino.YELLOW + "2 - Small (4-10)" + Casino.RESET);
            System.out.println(Casino.YELLOW + "3 - Specific Single (1-6)" + Casino.RESET);
            System.out.println(Casino.YELLOW + "4 - Specific Double" + Casino.RESET);
            System.out.println(Casino.YELLOW + "5 - Specific Triple" + Casino.RESET);
            System.out.println(Casino.YELLOW + "6 - Any Triple" + Casino.RESET);
            System.out.println(Casino.YELLOW + "7 - Sum (4-17)" + Casino.RESET);
            int choice = Casino.readIntInput("> ", 1, 7);
            int number = 0;
            if (choice == 3 || choice == 4 || choice == 5 || choice == 7) {
                number = Casino.readIntInput("Number/Sum: ", (choice == 7 ? 4 : 1), (choice == 7 ? 17 : 6));
            }
            int bet = Game.getBet();

            int[] dice = new int[3];
            for (int i = 0; i < 3; i++) {
                dice[i] = Casino.random.nextInt(6) + 1;
            }
            int sum = Arrays.stream(dice).sum();
            Map<Integer, Integer> counts = new HashMap<>();
            for (int d : dice) {
                counts.put(d, counts.getOrDefault(d, 0) + 1);
            }

            Casino.refreshScreen();
            Casino.printHeader();
            System.out.println(Casino.BOLD + Casino.BLUE + "== SIC BO ==" + Casino.RESET);
            Casino.printBalance();
            System.out.println("\nDice: " + Arrays.toString(dice) + " Sum: " + sum);

            int win = 0;
            if (choice == 1 && sum >= 11 && sum <= 17 && counts.size() != 1) win = bet;
            else if (choice == 2 && sum >= 4 && sum <= 10 && counts.size() != 1) win = bet;
            else if (choice == 3) {
                int appear = counts.getOrDefault(number, 0);
                win = bet * appear;
            } else if (choice == 4 && counts.getOrDefault(number, 0) >= 2) win = bet * 10;
            else if (choice == 5 && counts.getOrDefault(number, 0) == 3) win = bet * 180;
            else if (choice == 6 && counts.size() == 1) win = bet * 30;
            else if (choice == 7 && sum == number) {
                int payout = switch (number) {
                    case 4, 17 -> 60;
                    case 5, 16 -> 30;
                    case 6, 15 -> 17;
                    case 7, 14 -> 12;
                    case 8, 13 -> 8;
                    case 9, 12 -> 6;
                    case 10, 11 -> 6;
                    default -> 0;
                };
                win = bet * payout;
            }
            Game.handleWin(win, bet, "You win: ", "You lose: ");
        });
    }
}