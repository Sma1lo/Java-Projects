/**
 *
 *@author Sma1lo
 */

public class WheelOfFortune {
    static final int[] SECTORS = {1, 2, 5, 10, 20};
    public static void play() throws InterruptedException {
        Game.playTemplate("WHEEL OF FORTUNE", () -> {
            System.out.println("\nChoose sector (1-5 for 1x,2x,5x,10x,20x): ");
            int choice = Casino.readIntInput("> ", 1, 5);
            int bet = Game.getBet();

            System.out.println("\nSpinning...");
            try {
                Thread.sleep(2800);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            int result = SECTORS[Casino.random.nextInt(SECTORS.length)];
            System.out.println("\nResult: " + Casino.YELLOW + result + "x" + Casino.RESET);

            int win = (result == SECTORS[choice - 1]) ? bet * (result - 1) : 0;
            Game.handleWin(win, bet, "You win: ", "You lose: ");
        });
    }
}