/**
 *
 *@author Sma1lo
 */

public abstract class Game {
    static void playTemplate(String title, Runnable gameLogic) throws InterruptedException {
        if (Casino.money < Casino.MIN_BET) {
            System.out.println(Casino.RED + "Not enough money to bet." + Casino.RESET);
            Thread.sleep(1500);
            return;
        }
        Casino.refreshScreen();
        Casino.printHeader();
        System.out.println(Casino.BOLD + Casino.BLUE + "== " + title + " ==" + Casino.RESET);
        Casino.printBalance();
        gameLogic.run();
        if (Casino.money <= 0) {
            System.out.println(Casino.RED + "\nGame Over: You ran out of money." + Casino.RESET);
            System.exit(0);
        }
        if (Casino.bank <= 0) {
            System.out.println(Casino.RED + "\nCasino bankrupt! Game over." + Casino.RESET);
            System.exit(0);
        }
        System.out.println("\nPress Enter to continue...");
        Casino.scanner.nextLine();
    }

    static int getBet() {
        return Casino.readIntInput("Bet: ", Casino.MIN_BET, Casino.money);
    }

    static void handleWin(int win, int bet, String winMsg, String loseMsg) {
        if (win > 0) {
            if (win > Casino.bank) {
                win = Casino.bank;
                Casino.money += win;
                Casino.bank = 0;
                System.out.println(Casino.RED + "Casino bankrupt! You get remaining: " + win + Casino.RESET);
            } else {
                Casino.money += win;
                Casino.bank -= win;
                System.out.println(Casino.GREEN + winMsg + win + Casino.RESET);
            }
        } else {
            Casino.money -= bet;
            Casino.bank += bet;
            System.out.println(Casino.RED + loseMsg + bet + Casino.RESET);
        }
    }
}