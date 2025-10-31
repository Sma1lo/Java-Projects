/**
 *
 *@author Sma1lo
 */

public class CoinFlip {
    public static void play() throws InterruptedException {
        Game.playTemplate("COIN FLIP", () -> {
            System.out.print("Choose (h)eads or (t)ails: ");
            String choice = Casino.scanner.nextLine().trim().toLowerCase();
            if (!choice.equals("h") && !choice.equals("t")) {
                System.out.println(Casino.RED + "Invalid choice. Use 'h' or 't'." + Casino.RESET);
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return;
            }
            int bet = Game.getBet();

            System.out.print(Casino.YELLOW + "FLIPPING" + Casino.RESET);
            for (int i = 0; i < 7; i++) {
                System.out.print("." + (i % 2 == 0 ? "↑" : "↓"));
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.print("\r" + " ".repeat(10));
                System.out.print("\r");
            }

            boolean isHeads = Casino.random.nextBoolean();
            String result = isHeads ? "Heads" : "Tails";
            Casino.refreshScreen();
            Casino.printHeader();
            System.out.println(Casino.BOLD + Casino.BLUE + "== COIN FLIP ==" + Casino.RESET);
            Casino.printBalance();
            System.out.println("\nResult: " + Casino.BOLD + (isHeads ? Casino.GREEN : Casino.CYAN) + result + Casino.RESET);

            boolean winCondition = (isHeads && choice.equals("h")) || (!isHeads && choice.equals("t"));
            int win = winCondition ? bet : 0;
            Game.handleWin(win, bet, "You win: ", "You lose: ");
        });
    }
}