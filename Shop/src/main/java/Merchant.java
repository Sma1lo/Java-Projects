import java.util.Map;

/**
 *
 *@author Sma1lo
 */

public class Merchant extends Character {
    private Inventory inventory;

    public Merchant(String name, int money) {
        super(name, money);
        this.inventory = new Inventory();
        initializeMerchantStock();
    }

    private void initializeMerchantStock() {
        inventory.addItem(ItemList.APPLE, 10);
        inventory.addItem(ItemList.BANANA, 8);
        inventory.addItem(ItemList.ORANGE, 6);
        inventory.addItem(ItemList.GRAPES, 4);
        inventory.addItem(ItemList.WATERMELON, 2);
    }

    public boolean sellItem(ItemList item, int quantity, Character buyer) {
        if (!inventory.removeItem(item, quantity)) {
            return false;
        }

        int totalPrice = item.getPrice() * quantity;
        if (buyer.getMoney() >= totalPrice) {
            buyer.setMoney(buyer.getMoney() - totalPrice);
            this.setMoney(this.getMoney() + totalPrice);
            return true;
        }
        return false;
    }

    public void showMerchantStock() {
        System.out.println(ConsoleColor.YELLOW + "\n=== " + ConsoleColor.BLUE + "MERCHANT STOCK" + ConsoleColor.YELLOW + " ===" + ConsoleColor.RESET);
        Map<ItemList, Integer> stock = inventory.getItems();
        if (stock.isEmpty()) {
            System.out.println(ConsoleColor.RED +"Merchant has no items" + ConsoleColor.RESET);
        } else {
            for (Map.Entry<ItemList, Integer> entry : stock.entrySet()) {
                ItemList item = entry.getKey();
                System.out.printf("%s - %d coins (Quantity: %d)%n",
                        item.getName(), item.getPrice(), entry.getValue());
            }
        }
        System.out.println(ConsoleColor.YELLOW + "======================" + ConsoleColor.RESET);
    }

    public Inventory getInventory() {
        return inventory;
    }
}