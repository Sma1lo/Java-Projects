import java.util.*;

/**
 *
 *@author Sma1lo
 */

public class Bingo {
    public static void play() throws InterruptedException {
        Game.playTemplate("BINGO", () -> {
            int bet = Game.getBet();
            int[][] card = generateCard();
            printCard(card);

            Set<Integer> drawn = new HashSet<>();
            List<Integer> drawnList = new ArrayList<>();
            boolean won = false;
            int drawCount = 0;
            while (drawCount < 75 && !won) {
                int num = Casino.random.nextInt(75) + 1;
                if (!drawn.contains(num)) {
                    drawn.add(num);
                    drawnList.add(num);
                    System.out.print(Casino.YELLOW + num + " " + Casino.RESET);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    drawCount++;
                    if (isFullHouse(card, drawn)) won = true;
                }
            }

            Casino.refreshScreen();
            Casino.printHeader();
            System.out.println(Casino.BOLD + Casino.BLUE + "== BINGO ==" + Casino.RESET);
            Casino.printBalance();
            printCard(card);
            System.out.println("Drawn: " + Casino.CYAN + drawnList + Casino.RESET);

            int lines = countLines(card, drawn);
            boolean fullHouse = isFullHouse(card, drawn);
            int win = 0;
            if (fullHouse) {
                win = bet * 50;
            } else if (lines > 0) {
                win = bet * 5 * lines;
            }
            Game.handleWin(win, bet, fullHouse ? "Full House! You win: " : lines + " line(s)! You win: ", "You lose: ");
        });
    }

    private static int[][] generateCard() {
        int[][] card = new int[5][5];
        int[][] ranges = {{1,15}, {16,30}, {31,45}, {46,60}, {61,75}};
        for (int col = 0; col < 5; col++) {
            Set<Integer> nums = new HashSet<>();
            for (int row = 0; row < 5; row++) {
                if (col == 2 && row == 2) {
                    card[row][col] = 0;
                    continue;
                }
                int num;
                do {
                    num = Casino.random.nextInt(ranges[col][1] - ranges[col][0] + 1) + ranges[col][0];
                } while (nums.contains(num));
                nums.add(num);
                card[row][col] = num;
            }
        }
        return card;
    }

    private static void printCard(int[][] card) {
        System.out.println("\nYour Bingo card (B I N G O):");
        for (int[] row : card) {
            System.out.println(Casino.YELLOW + Arrays.toString(row) + Casino.RESET);
        }
    }

    private static int countLines(int[][] card, Set<Integer> drawn) {
        int lines = 0;

        for (int r = 0; r < 5; r++) {
            boolean line = true;
            for (int c = 0; c < 5; c++) {
                if (card[r][c] != 0 && !drawn.contains(card[r][c])) {
                    line = false;
                    break;
                }
            }
            if (line) lines++;
        }

        for (int c = 0; c < 5; c++) {
            boolean line = true;
            for (int r = 0; r < 5; r++) {
                if (card[r][c] != 0 && !drawn.contains(card[r][c])) {
                    line = false;
                    break;
                }
            }
            if (line) lines++;
        }

        boolean diag1 = true, diag2 = true;
        for (int i = 0; i < 5; i++) {
            if (card[i][i] != 0 && !drawn.contains(card[i][i])) diag1 = false;
            if (card[i][4 - i] != 0 && !drawn.contains(card[i][4 - i])) diag2 = false;
        }
        if (diag1) lines++;
        if (diag2) lines++;
        return lines;
    }

    private static boolean isFullHouse(int[][] card, Set<Integer> drawn) {
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                if (card[r][c] != 0 && !drawn.contains(card[r][c])) return false;
            }
        }
        return true;
    }
}