import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {
    private int coins;
    private List<Field> fields;
    private Map<String, Integer> inventory;

    public Player() {
        this.coins = 100;
        this.fields = new ArrayList<>();
        this.inventory = new HashMap<>();
        this.fields.add(new Field(50, 50));

        for (VegetableType veg : VegetableManager.getAllVegetables()) {
            inventory.put(veg.getName(), 0);
            inventory.put(veg.getName() + "_seeds", 0);
        }
    }

    public void addCoins(int amount) {
        this.coins += amount;
    }

    public boolean spendCoins(int amount) {
        if (coins >= amount) {
            coins -= amount;
            return true;
        }
        return false;
    }

    public void addToInventory(String itemName, int amount) {
        inventory.put(itemName, inventory.getOrDefault(itemName, 0) + amount);
    }

    public void removeFromInventory(String itemName, int amount) {
        int current = inventory.getOrDefault(itemName, 0);
        if (current >= amount) {
            inventory.put(itemName, current - amount);
        }
    }

    public int getInventoryCount(String itemName) {
        return inventory.getOrDefault(itemName, 0);
    }

    public void addField(Field field) {
        fields.add(field);
    }

    public void sellVegetable(String vegetableName, int amount) {
        VegetableType veg = VegetableManager.getByName(vegetableName);
        if (veg != null && getInventoryCount(vegetableName) >= amount) {
            removeFromInventory(vegetableName, amount);
            addCoins(amount * veg.getSellPrice());
        }
    }

    public boolean buySeeds(String vegetableName, int amount) {
        VegetableType veg = VegetableManager.getByName(vegetableName);
        if (veg != null) {
            int totalPrice = amount * veg.getSeedPrice();
            if (spendCoins(totalPrice)) {
                addToInventory(vegetableName + "_seeds", amount);
                return true;
            }
        }
        return false;
    }

    public int getCoins() {
        return coins;
    }

    public List<Field> getFields() {
        return fields;
    }

    public int getFieldCount() {
        return fields.size();
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }
}