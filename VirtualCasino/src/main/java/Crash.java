/**
 *
 *@author Sma1lo
 */

public class Crash {
    public static void play() throws InterruptedException {
        Game.playTemplate("CRASH", () -> {
            int bet = Game.getBet();
            System.out.println("\nMultiplier rising...");
            double multiplier = 1.0;
            double crashPoint = 1 + (-Math.log(1 - Casino.random.nextDouble()) / 1.0);
            boolean cashedOut = false;
            double cashOutMultiplier = 0;
            int i = 0;
            while (multiplier < crashPoint && i < 50) {
                multiplier = 1 + i * 0.1;
                Casino.refreshScreen();
                Casino.printHeader();
                System.out.println(Casino.BOLD + Casino.BLUE + "== CRASH ==" + Casino.RESET);
                Casino.printBalance();
                System.out.println("\nCurrent multiplier: " + Casino.YELLOW + String.format("%.2f", multiplier) + "x" + Casino.RESET);
                System.out.print(Casino.YELLOW + "1 - Cash out, 2 - Continue: " + Casino.RESET);
                int ch = Casino.readIntInput("", 1, 2);
                if (ch == 1) {
                    cashedOut = true;
                    cashOutMultiplier = multiplier;
                    break;
                }
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                i++;
            }
            int win = cashedOut ? (int) (bet * (cashOutMultiplier - 1)) : 0;
            System.out.println(cashedOut ? Casino.GREEN + "Cashed out at " + String.format("%.2f", cashOutMultiplier) + "x! Win: " + win + Casino.RESET
                    : Casino.RED + "Crashed at " + String.format("%.2f", crashPoint) + "x! You lose: " + bet + Casino.RESET);
            Game.handleWin(win, bet, "", "");
        });
    }
}