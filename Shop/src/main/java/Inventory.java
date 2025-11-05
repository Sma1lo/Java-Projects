import java.util.HashMap;
import java.util.Map;

/**
 *
 *@author Sma1lo
 */

public class Inventory {
    private Map<ItemList, Integer> items;

    public Inventory() {
        this.items = new HashMap<>();
    }

    public void addItem(ItemList item, int quantity) {
        items.put(item, items.getOrDefault(item, 0) + quantity);
    }

    public boolean removeItem(ItemList item, int quantity) {
        Integer current = items.get(item);
        if (current != null && current >= quantity) {
            items.put(item, current - quantity);
            if (items.get(item) == 0) {
                items.remove(item);
            }
            return true;
        }
        return false;
    }

    public void showInventory() {
        System.out.println(ConsoleColor.YELLOW + "\n=== " + ConsoleColor.BLUE + "INVENTORY" + ConsoleColor.YELLOW + " ===" + ConsoleColor.RESET);
        if (items.isEmpty()) {
            System.out.println(ConsoleColor.RED+ "Inventory is empty" + ConsoleColor.RESET);
        } else {
            for (Map.Entry<ItemList, Integer> entry : items.entrySet()) {
                System.out.printf("%s: %d%n", entry.getKey().getName(), entry.getValue());
            }
        }
        System.out.println(ConsoleColor.YELLOW + "=================" + ConsoleColor.RESET);
    }

    public Map<ItemList, Integer> getItems() {
        return items;
    }
}