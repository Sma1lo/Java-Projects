import java.util.ArrayList;
import java.util.List;

/**
 *
 *@author Sma1lo
 */

public class Blackjack {
    public static void play() throws InterruptedException {
        Game.playTemplate("BLACKJACK", () -> {
            int bet = Game.getBet();
            Deck deck = new Deck();
            List<Deck.Card> player = new ArrayList<>(), dealer = new ArrayList<>();
            player.add(deck.draw());
            dealer.add(deck.draw());
            player.add(deck.draw());
            dealer.add(deck.draw());

            printHands(player, dealer, true);

            int playerSum = getBJSum(player);
            int dealerVisible = Math.min(dealer.get(0).value, 10);
            if (playerSum == 21) {
                if (getBJSum(dealer) == 21) {
                    System.out.println(Casino.YELLOW + "Push (both Blackjack)." + Casino.RESET);
                } else {
                    int win = (int) (bet * 1.5);
                    Game.handleWin(win, bet, "Blackjack! You win: ", "");
                }
                return;
            }

            boolean doubled = false;
            if (player.size() == 2 && playerSum >= 9 && playerSum <= 11 && Casino.money >= bet) {
                System.out.print(Casino.YELLOW + "1 - Hit, 2 - Stand, 3 - Double: " + Casino.RESET);
                int ch = Casino.readIntInput("", 1, 3);
                if (ch == 3) {
                    bet *= 2;
                    doubled = true;
                    player.add(deck.draw());
                    playerSum = getBJSum(player);
                    printHands(player, dealer, true);
                } else if (ch == 1) {
                    player.add(deck.draw());
                    playerSum = getBJSum(player);
                    printHands(player, dealer, true);
                } else {
                }
            } else {
                System.out.print(Casino.YELLOW + "1 - Hit, 2 - Stand: " + Casino.RESET);
                while (playerSum < 21 && !doubled) {
                    int ch = Casino.readIntInput("", 1, 2);
                    if (ch == 1) {
                        player.add(deck.draw());
                        playerSum = getBJSum(player);
                        printHands(player, dealer, true);
                    } else break;
                }
            }

            if (playerSum > 21) {
                Game.handleWin(0, bet, "", "Bust! You lose: ");
                return;
            }

            int dealerSum = getBJSum(dealer);
            while (dealerSum < 17) {
                dealer.add(deck.draw());
                dealerSum = getBJSum(dealer);
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            printHands(player, dealer, false);
            if (dealerSum > 21) {
                Game.handleWin(bet, bet, "Dealer bust! You win: ", "");
            } else if (playerSum > dealerSum) {
                Game.handleWin(bet, bet, "You win: ", "");
            } else if (playerSum == dealerSum) {
                System.out.println(Casino.YELLOW + "Push." + Casino.RESET);
            } else {
                Game.handleWin(0, bet, "", "Dealer wins. You lose: ");
            }
        });
    }

    private static void printHands(List<Deck.Card> p, List<Deck.Card> d, boolean hideDealer) {
        Casino.refreshScreen();
        Casino.printHeader();
        System.out.println(Casino.BOLD + Casino.BLUE + "== BLACKJACK ==" + Casino.RESET);
        Casino.printBalance();
        System.out.println("\nYour cards: " + p + " (Sum: " + getBJSum(p) + ")");
        System.out.println("Dealer: " + (hideDealer ? d.get(0) + " [hidden]" : d + " (Sum: " + getBJSum(d) + ")"));
    }

    private static int getBJSum(List<Deck.Card> hand) {
        int sum = 0;
        int aces = 0;
        for (Deck.Card c : hand) {
            int v = Math.min(c.value, 10);
            if (v == 1) aces++;
            sum += v;
        }
        while (sum + 10 <= 21 && aces > 0) {
            sum += 10;
            aces--;
        }
        return sum;
    }
}