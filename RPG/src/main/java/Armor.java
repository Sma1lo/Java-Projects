
/**
 *
 *@author Sma1lo
 */


public class Armor extends Item implements Equippable {
    public Armor(int id, String name, String description, int value, int weight, int defense, int requiredLevel) {
        super(id, name, description, ItemType.ARMOR, value, weight, true);
        addStatBonus("health", defense * 5);
        addStatBonus("defense", defense);
        setRequiredLevel(requiredLevel);
    }

    @Override
    public void equip(Player player) {
        player.setHealth(player.getHealth() + getStatBonus("health"));
        player.setDefense(player.getDefense() + getStatBonus("defense"));
        System.out.println(ConsoleColor.WHITE + "Equipped armor: " + getName() + ConsoleColor.RESET);
    }

    @Override
    public void unequip(Player player) {
        int healthBonus = getStatBonus("health");
        int defenseBonus = getStatBonus("defense");

        player.setHealth(player.getHealth() - healthBonus);
        player.setDefense(player.getDefense() - defenseBonus);

        if (player.getHealth() < 1) player.setHealth(1);
        if (player.getDefense() < 0) player.setDefense(0);

        System.out.println(ConsoleColor.WHITE + "Unequipped armor: " + getName() + ConsoleColor.RESET);
    }
}