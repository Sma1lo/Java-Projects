import java.util.HashSet;
import java.util.Set;

/**
 *
 *@author Sma1lo
 */

public class Keno {
    static final int[] PAYOUTS = {0, 0, 0, 1, 2, 5, 10, 20, 50, 100, 1000};
    public static void play() throws InterruptedException {
        Game.playTemplate("KENO", () -> {
            System.out.println("\nChoose 1-10 numbers (1-80, separated by spaces): ");
            Set<Integer> playerNumbers = new HashSet<>();
            while (playerNumbers.size() < 1) {
                String[] input = Casino.scanner.nextLine().trim().split("\\s+");
                for (String s : input) {
                    try {
                        int num = Integer.parseInt(s);
                        if (num >= 1 && num <= 80 && playerNumbers.size() < 10) {
                            playerNumbers.add(num);
                        }
                    } catch (NumberFormatException e) {
                    }
                }
                if (playerNumbers.isEmpty()) {
                    System.out.println(Casino.RED + "Enter at least one valid number." + Casino.RESET);
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            int bet = Game.getBet();

            Set<Integer> drawn = new HashSet<>();
            for (int i = 0; i < 20; i++) {
                int num;
                do {
                    num = Casino.random.nextInt(80) + 1;
                } while (drawn.contains(num));
                drawn.add(num);
                System.out.print("\r" + Casino.YELLOW + "Ball " + (i + 1) + ": " + num + Casino.RESET);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println();

            int matches = 0;
            for (int num : playerNumbers) {
                if (drawn.contains(num)) matches++;
            }

            Casino.refreshScreen();
            Casino.printHeader();
            System.out.println(Casino.BOLD + Casino.BLUE + "== KENO ==" + Casino.RESET);
            Casino.printBalance();
            System.out.println("\nYour numbers: " + Casino.GREEN + playerNumbers + Casino.RESET);
            System.out.println("Drawn numbers: " + Casino.CYAN + drawn + Casino.RESET);
            System.out.println("Matches: " + Casino.YELLOW + matches + Casino.RESET);

            int payoutMultiplier = PAYOUTS[matches];
            int win = bet * payoutMultiplier;
            Game.handleWin(win, bet, "You win: " + win + " (" + payoutMultiplier + "x)", "You lose: ");
        });
    }
}