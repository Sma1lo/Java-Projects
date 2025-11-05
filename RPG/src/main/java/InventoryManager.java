import java.util.*;

/**
 *
 *@author Sma1lo
 */

public class InventoryManager {
    private List<Item> items;
    private int capacity;
    private int currentWeight;
    private int maxWeight;
    private Map<ItemType, Item> equippedItems;

    public InventoryManager(int capacity) {
        this.items = new ArrayList<>();
        this.capacity = capacity;
        this.currentWeight = 0;
        this.maxWeight = capacity * Constants.WEIGHT_PER_SLOT;
        this.equippedItems = new HashMap<>();
    }

    public boolean addItem(Item item) {
        if (items.size() >= capacity) {
            System.out.println(ConsoleColor.RED + "Inventory is full! Capacity: " + items.size() + "/" + capacity + ConsoleColor.RESET);
            return false;
        }
        if (currentWeight + item.getWeight() > maxWeight) {
            System.out.println(ConsoleColor.RED + "Too heavy. Weight: " + currentWeight + "/" + maxWeight + ConsoleColor.RESET);
            return false;
        }
        items.add(item);
        currentWeight += item.getWeight();
        System.out.println(ConsoleColor.WHITE + item.getName() + " added to inventory." + ConsoleColor.RESET);
        return true;
    }

    public boolean removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            Item item = items.get(index);
            return removeItem(item);
        }
        return false;
    }

    public boolean removeItem(Item item) {
        if (items.remove(item)) {
            currentWeight -= item.getWeight();
            if (equippedItems.containsValue(item)) {
                equippedItems.values().removeIf(eqItem -> eqItem.equals(item));
            }
            return true;
        }
        return false;
    }

    public Item getItem(int index) {
        if (index >= 0 && index < items.size()) {
            return items.get(index);
        }
        return null;
    }

    public void useItem(int index, Player player) {
        Item item = getItem(index);
        if (item != null) {
            useItem(item, player);
        } else {
            System.out.println(ConsoleColor.RED + "Invalid item index." + ConsoleColor.RESET);
        }
    }

    public void useItem(Item item, Player player) {
        if (!items.contains(item)) {
            System.out.println(ConsoleColor.RED + "Item not in inventory." + ConsoleColor.RESET);
            return;
        }
        if (player.getLevel() < item.getRequiredLevel()) {
            System.out.println(ConsoleColor.RED + "You need level " + item.getRequiredLevel() + " to use this." + ConsoleColor.RESET);
            return;
        }
        if (item instanceof Consumable cons) {
            cons.use(player);
            removeItem(item);
        } else if (item instanceof Equippable eq) {
            equipItem(item, player);
        } else {
            System.out.println(ConsoleColor.RED + "Cannot use this item." + ConsoleColor.RESET);
        }
    }

    public void equipItem(int index, Player player) {
        Item item = getItem(index);
        if (item != null) {
            equipItem(item, player);
        }
    }

    public void equipItem(Item item, Player player) {
        if (!(item instanceof Equippable eq)) {
            System.out.println(ConsoleColor.RED + "This item cannot be equipped." + ConsoleColor.RESET);
            return;
        }
        if (!items.contains(item)) {
            System.out.println(ConsoleColor.RED + "Item not in inventory." + ConsoleColor.RESET);
            return;
        }
        if (player.getLevel() < item.getRequiredLevel()) {
            System.out.println(ConsoleColor.RED + "You need level " + item.getRequiredLevel() + " to equip this." + ConsoleColor.RESET);
            return;
        }
        Item previousItem = equippedItems.get(item.getItemType());
        if (previousItem != null) {
            unequipItem(previousItem, player);
        }
        equippedItems.put(item.getItemType(), item);
        eq.equip(player);
    }

    public void unequipItem(ItemType itemType, Player player) {
        Item item = equippedItems.get(itemType);
        if (item != null) {
            unequipItem(item, player);
        }
    }

    public void unequipItem(Item item, Player player) {
        if (item instanceof Equippable eq && equippedItems.containsValue(item)) {
            equippedItems.remove(item.getItemType());
            eq.unequip(player);
        } else {
            System.out.println(ConsoleColor.RED + "Item is not equipped." + ConsoleColor.RESET);
        }
    }

    public boolean buyItem(Item item, Player player) {
        if (player.getGold() >= item.getValue()) {
            if (addItem(item)) {
                player.setGold(player.getGold() - item.getValue());
                System.out.println(ConsoleColor.WHITE + "Purchased: " + item.getName() + " for " + item.getValue() + " gold." + ConsoleColor.RESET);
                return true;
            }
        } else {
            System.out.println(ConsoleColor.RED + "Not enough gold. Need: " + item.getValue() + ", Have: " + player.getGold() + ConsoleColor.RESET);
        }
        return false;
    }

    public void sellItem(int index, Player player) {
        Item item = getItem(index);
        if (item != null) {
            sellItem(item, player);
        } else {
            System.out.println(ConsoleColor.RED + "Invalid item index." + ConsoleColor.RESET);
        }
    }

    public void sellItem(Item item, Player player) {
        if (items.contains(item)) {
            if (equippedItems.containsValue(item)) {
                unequipItem(item, player);
            }
            removeItem(item);
            int sellPrice = item.getValue() / 2;
            player.setGold(player.getGold() + sellPrice);
            System.out.println(ConsoleColor.WHITE + "Sold " + item.getName() + " for " + sellPrice + " gold." + ConsoleColor.RESET);
        } else {
            System.out.println(ConsoleColor.RED + "Item not in inventory." + ConsoleColor.RESET);
        }
    }

    public boolean hasItem(Item item) {
        return items.contains(item);
    }

    public Item getEquippedItem(ItemType itemType) {
        return equippedItems.get(itemType);
    }

    public void displayInventory() {
        items.sort(Comparator.comparing(Item::getItemType));
        System.out.println(ConsoleColor.CYAN + "\n=== " + ConsoleColor.PURPLE + "INVENTORY" + ConsoleColor.CYAN + " ===" + ConsoleColor.RESET);
        System.out.println(ConsoleColor.WHITE + "Capacity: " + items.size() + "/" + capacity + ConsoleColor.RESET);
        System.out.println(ConsoleColor.WHITE + "Weight: " + currentWeight + "/" + maxWeight + ConsoleColor.RESET);
        System.out.println(ConsoleColor.WHITE + "\nEquipped Items:" + ConsoleColor.RESET);
        if (equippedItems.isEmpty()) {
            System.out.println(ConsoleColor.WHITE + " None" + ConsoleColor.RESET);
        } else {
            for (Map.Entry<ItemType, Item> entry : equippedItems.entrySet()) {
                Item item = entry.getValue();
                System.out.println(ConsoleColor.WHITE + " " + entry.getKey() + ": " + item.getName() +
                        " (Value: " + item.getValue() + " gold)" + ConsoleColor.RESET);
            }
        }
        System.out.println(ConsoleColor.WHITE + "\nAll Items:" + ConsoleColor.RESET);
        if (items.isEmpty()) {
            System.out.println(ConsoleColor.RED + " Inventory is empty" + ConsoleColor.RESET);
        } else {
            for (int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
                String equippedIndicator = equippedItems.containsValue(item) ? ConsoleColor.CYAN + " [EQUIPPED]" + ConsoleColor.RESET : "";
                System.out.println((i + 1) + ". " + item.getName() +
                        " (" + item.getItemType() + ") - " +
                        item.getValue() + ConsoleColor.YELLOW + " gold" + equippedIndicator + ConsoleColor.RESET);
            }
        }
    }

    public void displayEquippableItems() {
        System.out.println(ConsoleColor.CYAN + "\n=== " + ConsoleColor.PURPLE + "EQUIPPABLE ITEMS" + ConsoleColor.CYAN + " ===" + ConsoleColor.RESET);
        boolean found = false;
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if (item.isEquippable()) {
                System.out.println((i + 1) + ". " + item.getName() + " (" + item.getItemType() + ")");
                found = true;
            }
        }
        if (!found) {
            System.out.println(ConsoleColor.RED + "No equippable items in inventory." + ConsoleColor.RESET);
        }
    }

    public List<Item> getItems() {
        return new ArrayList<>(items);
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCurrentWeight() {
        return currentWeight;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public Map<ItemType, Item> getEquippedItems() {
        return new HashMap<>(equippedItems);
    }

    public boolean hasSpace() {
        return items.size() < capacity && currentWeight < maxWeight;
    }

    public void clear() {
        items.clear();
        equippedItems.clear();
        currentWeight = 0;
    }
}