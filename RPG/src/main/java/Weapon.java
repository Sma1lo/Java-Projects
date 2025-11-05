
/**
 *
 *@author Sma1lo
 */

public class Weapon extends Item implements Equippable {
    public Weapon(int id, String name, String description, int value, int weight, int damage, int requiredLevel) {
        super(id, name, description, ItemType.WEAPON, value, weight, true);
        addStatBonus("damage", damage);
        setRequiredLevel(requiredLevel);
    }

    @Override
    public void equip(Player player) {
        player.setDamage(player.getDamage() + getStatBonus("damage"));
        System.out.println(ConsoleColor.WHITE + "Equipped weapon: " + getName() + ConsoleColor.RESET);
    }

    @Override
    public void unequip(Player player) {
        player.setDamage(player.getDamage() - getStatBonus("damage"));
        System.out.println(ConsoleColor.WHITE + "Unequipped weapon: " + getName() + ConsoleColor.RESET);
    }
}