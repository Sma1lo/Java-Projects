import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 *
 *@author Sma1lo
 */

public class Casino {
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String RED = "\u001B[31m";
    public static final String CYAN = "\u001B[36m";
    public static final String PURPLE = "\u001B[35m";
    public static final String BLUE = "\u001B[34m";
    public static final String BOLD = "\u001B[1m";
    static int money = 10000;
    static int bank = 50000000;
    static final int MIN_BET = 100;
    static String playerName = "";
    static int maxBalance = money;
    static final Scanner scanner = new Scanner(System.in);
    static final Random random = new Random();

    public static void main(String[] args) throws InterruptedException, IOException {
        loadGame();
        registerPlayer();
        while (true) {
            refreshScreen();
            printHeader();
            printBalance();
            printBank();
            showLeaderboard();
            System.out.println("\n" + BOLD + "Choose a game:" + RESET);
            System.out.println(YELLOW + "1 - Slots" + RESET);
            System.out.println(YELLOW + "2 - Roulette" + RESET);
            System.out.println(YELLOW + "3 - Blackjack" + RESET);
            System.out.println(YELLOW + "4 - Dice (Sic Bo)" + RESET);
            System.out.println(YELLOW + "5 - Coin Flip" + RESET);
            System.out.println(YELLOW + "6 - Baccarat" + RESET);
            System.out.println(YELLOW + "7 - Crash" + RESET);
            System.out.println(YELLOW + "8 - Keno" + RESET);
            System.out.println(YELLOW + "9 - Poker" + RESET);
            System.out.println(YELLOW + "10 - Wheel of Fortune" + RESET);
            System.out.println(YELLOW + "11 - Craps" + RESET);
            System.out.println(YELLOW + "12 - Bingo" + RESET);
            System.out.println(YELLOW + "13 - Restart" + RESET);
            System.out.println(YELLOW + "14 - Exit" + RESET);
            int choice = readIntInput("\n> ", 1, 14);
            if (choice == 14) {
                System.out.println(RED + "Exiting casino..." + RESET);
                updateLeaderboard();
                saveGame();
                break;
            }
            switch (choice) {
                case 1: SlotMachine.play(); break;
                case 2: Roulette.play(); break;
                case 3: Blackjack.play(); break;
                case 4: Dice.play(); break;
                case 5: CoinFlip.play(); break;
                case 6: Baccarat.play(); break;
                case 7: Crash.play(); break;
                case 8: Keno.play(); break;
                case 9: Poker.play(); break;
                case 10: WheelOfFortune.play(); break;
                case 11: Craps.play(); break;
                case 12: Bingo.play(); break;
                case 13: restartGame(); continue;
            }
            updateLeaderboard();
            saveGame();
            if (bank <= 0) {
                System.out.println(RED + "Casino is bankrupt! Game over." + RESET);
                Thread.sleep(2000);
                break;
            }
        }
        scanner.close();
    }

    static void registerPlayer() {
        refreshScreen();
        System.out.print("Enter your name: ");
        playerName = scanner.nextLine().trim();
        if (playerName.isEmpty()) playerName = "Player";
    }

    static void restartGame() throws InterruptedException {
        money = 10000;
        bank = 50000000;
        maxBalance = money;
        System.out.println(YELLOW + "Game restarted!" + RESET);
        Thread.sleep(1500);
    }

    static void saveGame() throws IOException {
        try (PrintWriter out = new PrintWriter("casino_save.txt")) {
            out.println(playerName);
            out.println(money);
            out.println(bank);
            out.println(maxBalance);
        }
    }

    static void loadGame() throws IOException {
        try (Scanner fileScanner = new Scanner(new File("casino_save.txt"))) {
            if (fileScanner.hasNextLine()) playerName = fileScanner.nextLine();
            if (fileScanner.hasNextInt()) money = fileScanner.nextInt();
            if (fileScanner.hasNextInt()) bank = fileScanner.nextInt();
            if (fileScanner.hasNextInt()) maxBalance = fileScanner.nextInt();
        } catch (FileNotFoundException e) {
            maxBalance = money;
        }
    }

    static void updateLeaderboard() throws IOException {
        maxBalance = Math.max(maxBalance, money);
        List<String[]> leaderboard = new ArrayList<>();
        try (Scanner fileScanner = new Scanner(new File("leaderboard.txt"))) {
            while (fileScanner.hasNextLine()) {
                String[] entry = fileScanner.nextLine().split(",");
                leaderboard.add(entry);
            }
        } catch (FileNotFoundException e) {
        }

        boolean updated = false;
        for (String[] entry : leaderboard) {
            if (entry[0].equals(playerName)) {
                entry[1] = String.valueOf(maxBalance);
                updated = true;
                break;
            }
        }
        if (!updated) {
            leaderboard.add(new String[]{playerName, String.valueOf(maxBalance)});
        }
        leaderboard.sort((a, b) -> Integer.parseInt(b[1]) - Integer.parseInt(a[1]));
        try (PrintWriter out = new PrintWriter("leaderboard.txt")) {
            for (int i = 0; i < Math.min(leaderboard.size(), 5); i++) {
                out.println(leaderboard.get(i)[0] + "," + leaderboard.get(i)[1]);
            }
        }
    }

    static void showLeaderboard() throws IOException {
        System.out.println("\n" + BOLD + "Leaderboard:" + RESET);
        try (Scanner fileScanner = new Scanner(new File("leaderboard.txt"))) {
            int rank = 1;
            while (fileScanner.hasNextLine() && rank <= 5) {
                String[] entry = fileScanner.nextLine().split(",");
                System.out.println(rank + ". " + entry[0] + ": " + CYAN + entry[1] + RESET);
                rank++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("  No records yet.");
        }
    }

    static int readIntInput(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                scanner.nextLine();
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println(RED + "Please enter a number between " + min + " and " + max + "." + RESET);
            } else {
                System.out.println(RED + "Invalid input. Please enter a number." + RESET);
                scanner.nextLine();
            }
        }
    }

    static void printHeader() {
        System.out.println(BOLD + PURPLE + "== VIRTUAL CASINO ==" + RESET);
        System.out.println(" ------------------");
    }

    static void printBalance() {
        System.out.println("Balance (" + playerName + "): " + (money > 0 ? GREEN : RED) + money + RESET);
    }

    static void printBank() {
        System.out.println("Casino bank: " + CYAN + bank + RESET);
    }

    static void refreshScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}