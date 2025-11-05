
/**
 *
 *@author Sma1lo
 */

public class Scroll extends Item implements Consumable {
    private String effectType;

    public Scroll(int id, String name, String description, int value, int weight,
                  int requiredLevel, String effectType) {
        super(id, name, description, ItemType.SCROLL, value, weight, false);
        setRequiredLevel(requiredLevel);
        this.effectType = effectType;
        initializeStats();
    }

    public Scroll(int id, String name, String description, int value, int weight, int requiredLevel) {
        this(id, name, description, value, weight, requiredLevel, "unknown");
    }

    private void initializeStats() {
        switch (effectType) {
            case "fireball" -> addStatBonus("damage", 25);
            case "healing" -> addStatBonus("heal", 40);
            case "lightning" -> addStatBonus("damage", 30);
            default -> addStatBonus("damage", 20);
        }
    }

    @Override
    public void use(Player player) {
        switch (effectType) {
            case "fireball" -> System.out.println(ConsoleColor.RED + "Fireball cast! Deals " +
                    getStatBonus("damage") + " damage!" + ConsoleColor.RESET);
            case "healing" -> {
                int healAmount = getStatBonus("heal");
                player.setHealth(player.getHealth() + healAmount);
                System.out.println(ConsoleColor.GREEN + "Healed " + healAmount + " HP!" + ConsoleColor.RESET);
            }
            case "lightning" -> System.out.println(ConsoleColor.YELLOW + "Lightning strike! Deals " +
                    getStatBonus("damage") + " damage!" + ConsoleColor.RESET);
            default -> System.out.println(ConsoleColor.BLUE + "Magical scroll used!" + ConsoleColor.RESET);
        }
    }

    public String getEffectType() {
        return effectType;
    }
}