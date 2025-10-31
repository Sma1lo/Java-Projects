import java.util.*;

/**
 *
 *@author Sma1lo
 */

public class Poker {
    static enum HandType {
        HIGH_CARD, PAIR, TWO_PAIR, THREE_OF_KIND, STRAIGHT, FLUSH, FULL_HOUSE, FOUR_OF_KIND, STRAIGHT_FLUSH
    }

    public static void play() throws InterruptedException {
        Game.playTemplate("POKER", () -> {
            int bet = Game.getBet();
            Deck deck = new Deck();
            List<Deck.Card> playerCards = new ArrayList<>(), dealerCards = new ArrayList<>(), community = new ArrayList<>();
            playerCards.add(deck.draw());
            playerCards.add(deck.draw());
            dealerCards.add(deck.draw());
            dealerCards.add(deck.draw());
            for (int i = 0; i < 5; i++) {
                community.add(deck.draw());
            }

            Casino.refreshScreen();
            Casino.printHeader();
            System.out.println(Casino.BOLD + Casino.BLUE + "== POKER ==" + Casino.RESET);
            Casino.printBalance();
            System.out.println("\nYour cards: " + Casino.GREEN + playerCards + Casino.RESET);
            System.out.println("Community: " + Casino.CYAN + community + Casino.RESET);
            System.out.println("Dealer's cards: [hidden]");

            System.out.print(Casino.YELLOW + "1 - Continue, 2 - Fold: " + Casino.RESET);
            int ch = Casino.readIntInput("", 1, 2);
            if (ch == 2) {
                Game.handleWin(0, bet, "", "You fold. You lose: ");
                return;
            }

            HandType playerHand = evaluateBestHand(playerCards, community);
            HandType dealerHand = evaluateBestHand(dealerCards, community);

            System.out.println("Dealer's cards: " + Casino.CYAN + dealerCards + Casino.RESET);
            System.out.println("Your hand: " + Casino.YELLOW + playerHand + Casino.RESET);
            System.out.println("Dealer's hand: " + Casino.YELLOW + dealerHand + Casino.RESET);

            if (playerHand.ordinal() > dealerHand.ordinal()) {
                int multiplier = switch (playerHand) {
                    case STRAIGHT_FLUSH -> 50;
                    case FOUR_OF_KIND -> 25;
                    case FULL_HOUSE -> 9;
                    case FLUSH -> 6;
                    case STRAIGHT -> 4;
                    case THREE_OF_KIND -> 3;
                    case TWO_PAIR -> 2;
                    case PAIR -> 1;
                    default -> 1;
                };
                int win = bet * multiplier;
                Game.handleWin(win, bet, "You win: ", "");
            } else if (playerHand == dealerHand) {
                System.out.println(Casino.YELLOW + "Tie. Bet returned." + Casino.RESET);
            } else {
                Game.handleWin(0, bet, "", "Dealer wins. You lose: ");
            }
        });
    }

    private static HandType evaluateBestHand(List<Deck.Card> hole, List<Deck.Card> community) {
        List<Deck.Card> all = new ArrayList<>();
        all.addAll(hole);
        all.addAll(community);
        HandType best = HandType.HIGH_CARD;

        for (int a = 0; a < 7; a++) {
            for (int b = a+1; b < 7; b++) {
                for (int c = b+1; c < 7; c++) {
                    for (int d = c+1; d < 7; d++) {
                        for (int e = d+1; e < 7; e++) {
                            List<Deck.Card> hand = List.of(all.get(a), all.get(b), all.get(c), all.get(d), all.get(e));
                            HandType type = evaluateHand(hand);
                            if (type.ordinal() > best.ordinal()) best = type;
                        }
                    }
                }
            }
        }
        return best;
    }

    private static HandType evaluateHand(List<Deck.Card> hand) {
        hand = new ArrayList<>(hand);
        hand.sort(Comparator.comparingInt(Deck.Card::getRankValue).reversed());
        List<Deck.Card> finalHand = hand;
        boolean flush = hand.stream().allMatch(c -> c.suit.equals(finalHand.get(0).suit));
        boolean straight = true;
        for (int i = 1; i < 5; i++) {
            if (hand.get(i-1).getRankValue() - hand.get(i).getRankValue() != 1) straight = false;
        }
        if (straight && flush) return HandType.STRAIGHT_FLUSH;
        if (flush) return HandType.FLUSH;
        if (straight) return HandType.STRAIGHT;

        Map<String, Integer> rankCount = new HashMap<>();
        for (Deck.Card c : hand) {
            rankCount.merge(c.rank, 1, Integer::sum);
        }
        if (rankCount.values().contains(4)) return HandType.FOUR_OF_KIND;
        if (rankCount.values().contains(3) && rankCount.values().contains(2)) return HandType.FULL_HOUSE;
        if (rankCount.values().contains(3)) return HandType.THREE_OF_KIND;
        long pairs = rankCount.values().stream().filter(v -> v == 2).count();
        if (pairs == 2) return HandType.TWO_PAIR;
        if (pairs == 1) return HandType.PAIR;
        return HandType.HIGH_CARD;
    }
}