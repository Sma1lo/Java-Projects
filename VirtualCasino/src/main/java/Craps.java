/**
 *
 *@author Sma1lo
 */

public class Craps {
    public static void play() throws InterruptedException {
        Game.playTemplate("CRAPS", () -> {
            System.out.println("\nChoose bet type:");
            System.out.println(Casino.YELLOW + "1 - Pass Line" + Casino.RESET);
            System.out.println(Casino.YELLOW + "2 - Don't Pass" + Casino.RESET);
            int choice = Casino.readIntInput("> ", 1, 2);
            int bet = Game.getBet();

            int sum = 0;
            try {
                sum = rollDice();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            int point = 0;
            boolean comeOutWin = (sum == 7 || sum == 11);
            boolean comeOutLose = (sum == 2 || sum == 3 || sum == 12);

            if (choice == 1) {
                if (comeOutWin) {
                    Game.handleWin(bet, bet, "Natural! You win: ", "");
                    return;
                } else if (comeOutLose) {
                    Game.handleWin(0, bet, "", "Craps! You lose: ");
                    return;
                } else {
                    point = sum;
                    System.out.println("Point is " + point);
                }
            } else {
                if (comeOutLose) {
                    Game.handleWin(bet, bet, "Craps! You win: ", "");
                    return;
                } else if (comeOutWin) {
                    Game.handleWin(0, bet, "", "Natural! You lose: ");
                    return;
                } else {
                    point = sum;
                    System.out.println("Point is " + point);
                }
            }

            while (true) {
                try {
                    sum = rollDice();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (sum == point) {
                    if (choice == 1) Game.handleWin(bet, bet, "Point made! You win: ", "");
                    else Game.handleWin(0, bet, "", "Point made! You lose: ");
                    break;
                } else if (sum == 7) {
                    if (choice == 1) Game.handleWin(0, bet, "", "Seven out! You lose: ");
                    else Game.handleWin(bet, bet, "Seven out! You win: ", "");
                    break;
                }
            }
        });
    }

    private static int rollDice() throws InterruptedException {
        System.out.println("\nRolling dice...");
        Thread.sleep(500);
        int die1 = Casino.random.nextInt(6) + 1;
        int die2 = Casino.random.nextInt(6) + 1;
        int sum = die1 + die2;
        System.out.println(Casino.YELLOW + die1 + " + " + die2 + " = " + sum + Casino.RESET);
        Thread.sleep(500);
        return sum;
    }
}