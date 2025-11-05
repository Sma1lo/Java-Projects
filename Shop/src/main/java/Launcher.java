import java.util.Scanner;

/**
 *
 *@author Sma1lo
 */

public class Launcher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your character name: ");
        String playerName = scanner.nextLine();

        System.out.print("Enter starting money: ");
        int startMoney = Integer.parseInt(scanner.nextLine());

        Character player = new Character(playerName, startMoney) {};
        Merchant merchant = new Merchant("John", 500);

        ShopLogic shop = new ShopLogic(merchant);
        shop.startShopping(player);

        System.out.println(ConsoleColor.YELLOW + "\n=== " + ConsoleColor.BLUE + "FINAL SUMMARY" + ConsoleColor.YELLOW + " ===" + ConsoleColor.RESET);
        System.out.printf("Player: %s%n", player.getName());
        System.out.printf("Player money: %d coins%n", player.getMoney());
        player.getInventory().showInventory();

        System.out.printf("\nMerchant: %s%n", merchant.getName());
        System.out.printf("Merchant money: %d coins%n", merchant.getMoney());
        merchant.getInventory().showInventory();

        scanner.close();
    }
}