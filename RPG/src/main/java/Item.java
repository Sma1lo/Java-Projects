import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 *@author Sma1lo
 */

public abstract class Item extends Entity implements HasStats {
    protected final String description;
    protected final ItemType itemType;
    protected final int value;
    protected final int weight;
    protected final boolean isEquippable;
    protected final Map<String, Integer> stats;
    protected int requiredLevel = 1;

    public Item(int id, String name, String description, ItemType itemType,
                int value, int weight, boolean isEquippable) {
        super(id, name);
        this.description = description != null ? description : "";
        this.itemType = itemType;
        this.value = Math.max(value, 0);
        this.weight = Math.max(weight, 0);
        this.isEquippable = isEquippable;
        this.stats = new HashMap<>();
    }

    @Override
    public void addStatBonus(String stat, int bonus) {
        if (stat != null && !stat.isEmpty()) {
            stats.put(stat, bonus);
        }
    }

    @Override
    public Integer getStatBonus(String stat) {
        return stats.getOrDefault(stat, 0);
    }

    @Override
    public Map<String, Integer> getAllStatBonuses() {
        return new HashMap<>(stats);
    }

    public boolean isConsumable() {
        return this instanceof Consumable;
    }

    public boolean isEquippable() {
        return isEquippable;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public void setRequiredLevel(int requiredLevel) {
        this.requiredLevel = Math.max(requiredLevel, 1);
    }

    public String getFullInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(ConsoleColor.WHITE + "Name: " + ConsoleColor.RESET + name + "\n");
        sb.append(ConsoleColor.WHITE + "Type: " + ConsoleColor.RESET + (itemType != null ? itemType.getDisplayName() : "Unknown") + "\n");
        sb.append(ConsoleColor.WHITE + "Description: " + ConsoleColor.RESET + description + "\n");
        sb.append(ConsoleColor.WHITE + "Value: " + ConsoleColor.RESET + value + " gold\n");
        sb.append(ConsoleColor.WHITE + "Weight: " + ConsoleColor.RESET + weight + "\n");
        sb.append(ConsoleColor.WHITE + "Equippable: " + ConsoleColor.RESET + (isEquippable ? "Yes" : "No") + "\n");
        sb.append(ConsoleColor.WHITE + "Required Level: " + ConsoleColor.RESET + requiredLevel + "\n");
        if (!stats.isEmpty()) {
            sb.append(ConsoleColor.WHITE + "Stats:\n" + ConsoleColor.RESET);
            for (Map.Entry<String, Integer> entry : stats.entrySet()) {
                sb.append(ConsoleColor.WHITE + " " + entry.getKey() + ": +" + entry.getValue() + "\n" + ConsoleColor.RESET);
            }
        }
        return sb.toString();
    }

    public ItemType getItemType() {
        return itemType;
    }

    public int getValue() {
        return value;
    }

    public int getWeight() {
        return weight;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Item item = (Item) obj;
        return id == item.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}