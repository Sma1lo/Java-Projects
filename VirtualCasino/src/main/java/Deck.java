import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 *@author Sma1lo
 */

public class Deck {
    private List<Card> cards = new ArrayList<>();
    static class Card {
        int value;
        String suit;
        String rank;
        Card(String rank, String suit, int value) {
            this.rank = rank;
            this.suit = suit;
            this.value = value;
        }
        @Override
        public String toString() {
            return rank + suit;
        }
        int getRankValue() {
            return "23456789TJQKA".indexOf(rank.charAt(0));
        }
    }

    Deck() {
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        int[] values = {2,3,4,5,6,7,8,9,10,10,10,10,1};
        String[] suits = {"♠", "♥", "♦", "♣"};
        for (int i = 0; i < ranks.length; i++) {
            for (String suit : suits) {
                cards.add(new Card(ranks[i], suit, values[i]));
            }
        }
        Collections.shuffle(cards, Casino.random);
    }

    Card draw() {
        return cards.remove(0);
    }
}