import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *@author Sma1lo
 */

public class SlotMachine {
    static final String[] symbols = {"$", "¢", "€", "£", "¥", "@", "#", "&", "%", "*", "W"};
    static final Map<String, Integer> multipliers = new HashMap<>();
    static final Map<String, Integer> weights = new HashMap<>();
    static final List<String> weightedSymbols = new ArrayList<>();
    static {
        multipliers.put("$", 15);
        multipliers.put("¢", 10);
        multipliers.put("€", 12);
        multipliers.put("£", 11);
        multipliers.put("¥", 9);
        multipliers.put("@", 8);
        multipliers.put("#", 7);
        multipliers.put("&", 6);
        multipliers.put("%", 5);
        multipliers.put("*", 4);
        multipliers.put("W", 3);
        weights.put("$", 5);
        weights.put("¢", 7);
        weights.put("€", 6);
        weights.put("£", 6);
        weights.put("¥", 7);
        weights.put("@", 8);
        weights.put("#", 10);
        weights.put("&", 12);
        weights.put("%", 14);
        weights.put("*", 20);
        weights.put("W", 3);
        for (Map.Entry<String, Integer> entry : weights.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                weightedSymbols.add(entry.getKey());
            }
        }
    }
    public static void play() throws InterruptedException {
        Game.playTemplate("SLOTS", () -> {
            int bet = Game.getBet();
            String[] current = new String[3];
            int spins = 30;
            System.out.println("\n\n\n");
            for (int i = 0; i < spins; i++) {
                for (int j = 0; j < 3; j++) {
                    current[j] = weightedSymbols.get(Casino.random.nextInt(weightedSymbols.size()));
                }
                System.out.print("\033[2A");
                System.out.print("\r" + formatSlots(current, i % 2 == 0) + "   \n");
                System.out.print(Casino.YELLOW + "Spinning" + ".".repeat(i % 4) + Casino.RESET + "\n");
                try {
                    Thread.sleep(50 + i * 5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.print("\033[1A");
            System.out.print("\r");

            Map<String, Integer> count = new HashMap<>();
            for (String s : current) count.put(s, count.getOrDefault(s, 0) + 1);
            int wilds = count.getOrDefault("W", 0);
            count.remove("W");
            String matched = null;
            int same = 0;
            for (Map.Entry<String, Integer> entry : count.entrySet()) {
                if (entry.getValue() > same) {
                    same = entry.getValue();
                    matched = entry.getKey();
                }
            }
            int win = 0;
            String msg = "Lost: ";
            if (wilds == 3) {
                win = bet * 10;
                msg = "Triple WILD! Win: ";
            } else if ((same + wilds) == 3 && same > 0) {
                win = bet * multipliers.get(matched);
                msg = "Jackpot! Win: ";
            } else if (same == 2 && wilds == 0) {
                win = (int)(bet * 1.5);
                msg = "Two matched. Win: ";
            } else {
                win = 0;
            }
            Casino.refreshScreen();
            Casino.printHeader();
            System.out.println(Casino.BOLD + Casino.BLUE + "== SLOTS ==" + Casino.RESET);
            Casino.printBalance();
            System.out.println("\nResult: " + formatSlots(current, true));
            Game.handleWin(win, bet, msg, "You lose: ");
        });
    }
    private static String formatSlots(String[] slots, boolean pulse) {
        StringBuilder sb = new StringBuilder();
        for (String slot : slots) {
            sb.append("| ").append(pulse ? Casino.BOLD + Casino.PURPLE + slot + Casino.RESET : slot).append(" ");
        }
        sb.append("|");
        return sb.toString();
    }
}