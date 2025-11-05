import java.util.Scanner;

/**
 *
 *@author Sma1lo
 */

public class ShopLogic {
    private Scanner scanner;
    private Merchant merchant;

    public ShopLogic(Merchant merchant) {
        this.scanner = new Scanner(System.in);
        this.merchant = merchant;
    }

    public void showProducts() {
        merchant.showMerchantStock();
    }

    public void startShopping(Character character) {
        System.out.printf("Welcome to %s's shop, %s!%n", merchant.getName(), character.getName());
        System.out.printf("Your balance: %d coins%n", character.getMoney());
        System.out.printf("Merchant balance: %d coins%n", merchant.getMoney());

        while (true) {
            showMainMenu();
            System.out.print("Select option: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        showProducts();
                        break;
                    case 2:
                        buyItem(character);
                        break;
                    case 3:
                        character.getInventory().showInventory();
                        break;
                    case 4:
                        merchant.getInventory().showInventory();
                        break;
                    case 0:
                        System.out.println(ConsoleColor.GREEN + "Thank you for shopping!" + ConsoleColor.RESET);
                        return;
                    default:
                        System.out.println(ConsoleColor.RED + "Invalid option!" + ConsoleColor.RESET);
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a number!");
            }
        }
    }

    private void showMainMenu() {
        System.out.println(ConsoleColor.YELLOW + "\n=== " + ConsoleColor.BLUE + "MAIN MENU" + ConsoleColor.YELLOW + " ===" + ConsoleColor.RESET);
        System.out.println("1. View merchant stock");
        System.out.println("2. Buy item");
        System.out.println("3. View my inventory");
        System.out.println("4. View merchant inventory");
        System.out.println("0. Exit shop");
        System.out.println(ConsoleColor.YELLOW + "=================" + ConsoleColor.RESET);
    }

    private void buyItem(Character character) {
        showProducts();
        System.out.print("Select item number to buy: ");

        try {
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice < 1 || choice > ItemList.values().length) {
                System.out.println(ConsoleColor.RED + "Invalid item number!" + ConsoleColor.RESET);
                return;
            }

            ItemList selectedItem = ItemList.values()[choice - 1];

            System.out.print("Enter quantity: ");
            int quantity = Integer.parseInt(scanner.nextLine());

            if (quantity <= 0) {
                System.out.println(ConsoleColor.RED + "Quantity must be positive!" + ConsoleColor.RESET);
                return;
            }

            processPurchase(character, selectedItem, quantity);

        } catch (NumberFormatException e) {
            System.out.println(ConsoleColor.RED + "Please enter a valid number!" + ConsoleColor.RESET);
        }
    }

    private void processPurchase(Character character, ItemList item, int quantity) {
        int totalPrice = item.getPrice() * quantity;

        System.out.printf("\nPurchase: %d x %s%n", quantity, item.getName());
        System.out.printf("Total price: %d coins%n", totalPrice);
        System.out.printf("Your balance: %d coins%n", character.getMoney());

        if (merchant.sellItem(item, quantity, character)) {
            character.getInventory().addItem(item, quantity);
            System.out.printf("Successfully bought %d x %s for %d coins!%n",
                    quantity, item.getName(), totalPrice);
            System.out.printf("Remaining money: %d coins%n", character.getMoney());
        } else {
            System.out.println(ConsoleColor.RED + "Purchase failed! Check merchant stock and your money." + ConsoleColor.RESET);
        }
    }
}