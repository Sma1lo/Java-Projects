
/**
 *
 *@author Sma1lo
 */

public class Potion extends Item implements Consumable {
    public Potion(int id, String name, String description, int value, int weight, String bonusType, int amount, int requiredLevel) {
        super(id, name, description, ItemType.POTION, value, weight, false);
        addStatBonus(bonusType, amount);
        setRequiredLevel(requiredLevel);
    }

    @Override
    public void use(Player player) {
        Integer heal = getStatBonus("heal");
        Integer mana = getStatBonus("mana");
        if (heal > 0) {
            player.setHealth(player.getHealth() + heal);
            System.out.println(ConsoleColor.WHITE + "Restored " + ConsoleColor.RED + heal + " HP!" + ConsoleColor.RESET);
        }
        if (mana > 0) {
            player.setMana(player.getMana() + mana);
            System.out.println(ConsoleColor.WHITE + "Restored " + ConsoleColor.BLUE + mana + " MP!" + ConsoleColor.RESET);
        }
    }
}