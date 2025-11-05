import java.util.ArrayList;
import java.util.List;

/**
 *
 *@author Sma1lo
 */

public class Shop {
    private List<Item> stock = new ArrayList<>();

    public Shop() {
        stock.add(GameLogic.itemManager.createHealthPotion("Health Potion", "Restores 30 HP", 25, 1, 30, 1));
        stock.add(GameLogic.itemManager.createManaPotion("Mana Potion", "Restores 50 MP", 35, 1, 50, 1));
        stock.add(GameLogic.itemManager.createWeapon("Iron Sword", "A basic iron sword", 50, 5, 8, 1));
        stock.add(GameLogic.itemManager.createArmor("Leather Armor", "Basic leather protection", 40, 8, 2, 1));
    }

    public void showShopMenu(Player player) {
        boolean inShop = true;
        while (inShop) {
            System.out.println(ConsoleColor.CYAN + "\n=== " + ConsoleColor.PURPLE + "SHOP" + ConsoleColor.CYAN + " ===" + ConsoleColor.RESET);
            System.out.println(ConsoleColor.YELLOW + "Your Gold: " + player.getGold() + ConsoleColor.RESET);
            for (int i = 0; i < stock.size(); i++) {
                Item item = stock.get(i);
                System.out.println((i + 1) + ". " + item.getName() + " - " + item.getValue() + " gold");
            }
            System.out.println(ConsoleColor.WHITE + "0. Back" + ConsoleColor.RESET);
            System.out.print("Choose item to buy: ");
            int choice = GameLogic.scanner.nextInt();
            GameLogic.refreshScreen();
            if (choice == 0) {
                inShop = false;
            } else if (choice > 0 && choice <= stock.size()) {
                Item item = stock.get(choice - 1);
                if (player.getInventory().buyItem(item, player)) {
                }

            } else {
                System.out.println(ConsoleColor.RED + "Invalid choice." + ConsoleColor.RESET);
            }
        }
    }
}