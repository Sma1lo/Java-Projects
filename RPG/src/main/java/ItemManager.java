import java.util.*;

/**
 *
 *@author Sma1lo
 */

public class ItemManager {
    private Map<Integer, Item> itemTemplates;
    private int nextId;
    private Random random;

    public ItemManager() {
        this.itemTemplates = new HashMap<>();
        this.nextId = 1;
        this.random = new Random();
        initializeDefaultItems();
    }

    private void initializeDefaultItems() {
        createWeapon("Iron Sword", "A basic iron sword", 50, 5, 8, 1);
        createWeapon("Steel Sword", "A sturdy steel sword", 100, 6, 12, 3);
        createWeapon("Staff of Fire", "A magical fire staff", 150, 3, 15, 5);
        createWeapon("Hunter's Bow", "A sturdy wooden bow", 80, 4, 10, 2);
        createWeapon("Dragon Slayer", "A mighty dragon-slaying sword", 500, 15, 25, 10);
        createArmor("Leather Armor", "Basic leather protection", 40, 8, 2, 1);
        createArmor("Chain Mail", "Flexible chain armor", 120, 12, 5, 4);
        createArmor("Plate Armor", "Heavy plate armor", 250, 20, 8, 7);
        createArmor("Mage Robes", "Enchanted wizard robes", 90, 4, 3, 2);
        createHealthPotion("Health Potion", "Restores 30 HP", 25, 1, 30, 1);
        createHealthPotion("Greater Health Potion", "Restores 60 HP", 50, 1, 60, 3);
        createManaPotion("Mana Potion", "Restores 50 MP", 35, 1, 50, 1);
        createManaPotion("Greater Mana Potion", "Restores 100 MP", 70, 1, 100, 4);
        createScroll("Scroll of Fireball", "Casts a powerful fireball", 80, 1, 3, "fireball");
        createScroll("Scroll of Healing", "Heals moderate wounds", 60, 1, 2, "healing");
        createScroll("Scroll of Lightning", "Casts a lightning bolt", 100, 1, 4, "lightning");
    }

    public Weapon createWeapon(String name, String description, int value, int weight, int damage, int requiredLevel) {
        Weapon weapon = new Weapon(nextId++, name, description, value, weight, damage, requiredLevel);
        itemTemplates.put(weapon.getId(), weapon);
        return weapon;
    }

    public Armor createArmor(String name, String description, int value, int weight, int defense, int requiredLevel) {
        Armor armor = new Armor(nextId++, name, description, value, weight, defense, requiredLevel);
        itemTemplates.put(armor.getId(), armor);
        return armor;
    }

    public Potion createHealthPotion(String name, String description, int value, int weight, int healAmount, int requiredLevel) {
        Potion potion = new Potion(nextId++, name, description, value, weight, "heal", healAmount, requiredLevel);
        itemTemplates.put(potion.getId(), potion);
        return potion;
    }

    public Potion createManaPotion(String name, String description, int value, int weight, int manaAmount, int requiredLevel) {
        Potion potion = new Potion(nextId++, name, description, value, weight, "mana", manaAmount, requiredLevel);
        itemTemplates.put(potion.getId(), potion);
        return potion;
    }

    public Scroll createScroll(String name, String description, int value, int weight,
                               int requiredLevel, String effectType) {
        Scroll scroll = new Scroll(nextId++, name, description, value, weight,
                requiredLevel, effectType);
        itemTemplates.put(scroll.getId(), scroll);
        return scroll;
    }

    public Item getItemById(int id) {
        Item template = itemTemplates.get(id);
        if (template != null) {
            return copyItem(template);
        }
        return null;
    }

    public Item getRandomItem() {
        List<Item> items = new ArrayList<>(itemTemplates.values());
        if (items.isEmpty()) return null;
        int index = random.nextInt(items.size());
        Item template = items.get(index);
        return copyItem(template);
    }

    private Item copyItem(Item original) {
        Item copy = null;
        if (original instanceof Weapon orig) {
            copy = new Weapon(orig.getId(), orig.getName(), orig.getDescription(),
                    orig.getValue(), orig.getWeight(),
                    orig.getStatBonus("damage"), orig.getRequiredLevel());
        } else if (original instanceof Armor orig) {
            copy = new Armor(orig.getId(), orig.getName(), orig.getDescription(),
                    orig.getValue(), orig.getWeight(),
                    orig.getStatBonus("defense"), orig.getRequiredLevel());
        } else if (original instanceof Potion orig) {
            String bonusType = orig.getStatBonus("heal") > 0 ? "heal" : "mana";
            int amount = Math.max(orig.getStatBonus("heal"), orig.getStatBonus("mana"));
            copy = new Potion(orig.getId(), orig.getName(), orig.getDescription(),
                    orig.getValue(), orig.getWeight(), bonusType, amount,
                    orig.getRequiredLevel());
        } else if (original instanceof Scroll orig) {
            copy = new Scroll(orig.getId(), orig.getName(), orig.getDescription(),
                    orig.getValue(), orig.getWeight(), orig.getRequiredLevel(),
                    orig.getEffectType());
        }

        if (copy != null) {
            for (Map.Entry<String, Integer> stat : original.getAllStatBonuses().entrySet()) {
                copy.addStatBonus(stat.getKey(), stat.getValue());
            }
        }
        return copy;
    }
}