import java.util.*;

/**
 *
 *@author Sma1lo
 */

public class Roulette {
    static final Set<Integer> redNumbers = new HashSet<>(Arrays.asList(1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36));
    public static void play() throws InterruptedException {
        if (Casino.money < Casino.MIN_BET) {
            System.out.println(Casino.RED + "Not enough money to bet." + Casino.RESET);
            Thread.sleep(1500);
            return;
        }
        Map<String, Integer> bets = new HashMap<>();
        while (true) {
            Casino.refreshScreen();
            Casino.printHeader();
            System.out.println(Casino.BOLD + Casino.BLUE + "== ROULETTE ==" + Casino.RESET);
            Casino.printBalance();
            Casino.printBank();
            System.out.println("Current bets: " + (bets.isEmpty() ? "None" : bets));
            System.out.println("\nBet type: 1 - Color, 2 - Zero, 3 - Even/Odd, 4 - Range, 5 - Number, 0 - Spin");
            String input = Casino.scanner.nextLine().toLowerCase();
            if (input.equals("0")) {
                if (bets.isEmpty()) {
                    System.out.println(Casino.RED + "Place a bet first." + Casino.RESET);
                    Thread.sleep(1500);
                    continue;
                }
                break;
            }
            String key = "";
            switch (input) {
                case "1":
                    System.out.print("Color (r/b): ");
                    String c = Casino.scanner.nextLine().toLowerCase();
                    key = "color:" + (c.startsWith("r") ? "red" : "black");
                    break;
                case "2":
                    key = "zero";
                    break;
                case "3":
                    System.out.print("Even/Odd: ");
                    String eo = Casino.scanner.nextLine().toLowerCase();
                    key = "eo:" + eo;
                    break;
                case "4":
                    System.out.print("Range (1-12,13-24,25-36): ");
                    String range = Casino.scanner.nextLine();
                    key = "range:" + range;
                    break;
                case "5":
                    System.out.print("Number (0-36): ");
                    int num = Integer.parseInt(Casino.scanner.nextLine());
                    key = "num:" + num;
                    break;
                default:
                    System.out.println(Casino.RED + "Invalid." + Casino.RESET);
                    Thread.sleep(1500);
                    continue;
            }
            int amount = Casino.readIntInput("Bet: ", Casino.MIN_BET, Casino.money - bets.values().stream().mapToInt(Integer::intValue).sum());
            bets.merge(key, amount, Integer::sum);
        }

        Casino.refreshScreen();
        Casino.printHeader();
        System.out.println(Casino.BOLD + Casino.BLUE + "== ROULETTE ==" + Casino.RESET);
        Casino.printBalance();
        System.out.println("\nSpinning...");
        Thread.sleep(3600);
        int result = Casino.random.nextInt(37);
        String color = result == 0 ? "green" : redNumbers.contains(result) ? "red" : "black";
        System.out.println("Result: " + result + " (" + color + ")");

        int totalWin = 0;
        int totalBet = bets.values().stream().mapToInt(Integer::intValue).sum();
        for (Map.Entry<String, Integer> entry : bets.entrySet()) {
            String key = entry.getKey();
            int amount = entry.getValue();
            int payout = 0;
            if (key.equals("zero") && result == 0) payout = 35 * amount;
            else if (key.startsWith("color:") && key.substring(6).equals(color)) payout = amount;
            else if (key.startsWith("eo:") && result != 0) {
                String eo = key.substring(3);
                if ((eo.equals("even") && result % 2 == 0) || (eo.equals("odd") && result % 2 == 1)) payout = amount;
            } else if (key.startsWith("range:")) {
                String range = key.substring(6);
                int low = range.equals("1-12") ? 1 : range.equals("13-24") ? 13 : 25;
                int high = low + 11;
                if (result >= low && result <= high) payout = 2 * amount;
            } else if (key.startsWith("num:") && Integer.parseInt(key.substring(4)) == result) payout = 35 * amount;
            if (payout > 0) {
                totalWin += payout;
                System.out.println(Casino.GREEN + "Win on " + key + ": " + payout + Casino.RESET);
            }
        }
        Casino.money += totalWin - totalBet;
        Casino.bank += totalBet - totalWin;
        if (totalWin == 0) System.out.println(Casino.RED + "No wins." + Casino.RESET);
    }
}