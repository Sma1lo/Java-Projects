import java.util.ArrayList;
import java.util.List;

/**
 *
 *@author Sma1lo
 */

public class Baccarat {
    public static void play() throws InterruptedException {
        Game.playTemplate("BACCARAT", () -> {
            System.out.println("\nChoose bet type:");
            System.out.println(Casino.YELLOW + "1 - Player" + Casino.RESET);
            System.out.println(Casino.YELLOW + "2 - Banker" + Casino.RESET);
            System.out.println(Casino.YELLOW + "3 - Tie" + Casino.RESET);
            int choice = Casino.readIntInput("> ", 1, 3);
            int bet = Game.getBet();

            Deck deck = new Deck();
            List<Deck.Card> playerHand = new ArrayList<>(), bankerHand = new ArrayList<>();
            playerHand.add(deck.draw());
            bankerHand.add(deck.draw());
            playerHand.add(deck.draw());
            bankerHand.add(deck.draw());

            int playerScore = getScore(playerHand);
            int bankerScore = getScore(bankerHand);

            if (playerScore <= 5) {
                Deck.Card thirdPlayer = deck.draw();
                playerHand.add(thirdPlayer);
                playerScore = getScore(playerHand);

                if (bankerScore <= 2) {
                    bankerHand.add(deck.draw());
                } else if (bankerScore == 3 && thirdPlayer.value != 8) {
                    bankerHand.add(deck.draw());
                } else if (bankerScore == 4 && thirdPlayer.value >= 2 && thirdPlayer.value <= 7) {
                    bankerHand.add(deck.draw());
                } else if (bankerScore == 5 && thirdPlayer.value >= 4 && thirdPlayer.value <= 7) {
                    bankerHand.add(deck.draw());
                } else if (bankerScore == 6 && thirdPlayer.value >= 6 && thirdPlayer.value <= 7) {
                    bankerHand.add(deck.draw());
                }
                bankerScore = getScore(bankerHand);
            } else if (bankerScore <= 5) {
                bankerHand.add(deck.draw());
                bankerScore = getScore(bankerHand);
            }

            Casino.refreshScreen();
            Casino.printHeader();
            System.out.println(Casino.BOLD + Casino.BLUE + "== BACCARAT ==" + Casino.RESET);
            Casino.printBalance();
            System.out.println("\nPlayer hand: " + playerHand + " (Score: " + Casino.GREEN + playerScore + Casino.RESET + ")");
            System.out.println("Banker hand: " + bankerHand + " (Score: " + Casino.CYAN + bankerScore + Casino.RESET + ")");

            int win = 0;
            if (playerScore > bankerScore && choice == 1) {
                win = bet;
                Game.handleWin(win, bet, "Player wins! You win: ", "You lose: ");
            } else if (bankerScore > playerScore && choice == 2) {
                win = (int) (bet * 0.95);
                Game.handleWin(win, bet, "Banker wins! You win: ", "You lose: ");
            } else if (playerScore == bankerScore && choice == 3) {
                win = bet * 8;
                Game.handleWin(win, bet, "Tie! You win: ", "You lose: ");
            } else {
                Game.handleWin(0, bet, "", "You lose: ");
            }
        });
    }

    private static int getScore(List<Deck.Card> hand) {
        int sum = 0;
        for (Deck.Card c : hand) {
            sum += c.value;
        }
        return sum % 10;
    }
}