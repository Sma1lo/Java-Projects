
/**
 *
 *@author Sma1lo
 */


public abstract class Character extends Entity {
    protected int damage;
    protected int health;
    protected int defense;

    public Character(int id, String name, int damage, int health, int defense) {
        super(id, name);
        this.damage = damage;
        this.health = health;
        this.defense = defense;
    }

    public int getDamage() {
        return damage;
    }

    public int getHealth() {
        return health;
    }

    public int getDefense() {
        return defense;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public abstract void attack(Character target);

    public boolean isAlive() {
        return health > 0;
    }

    public void takeDamage(int damage) {
        int effectiveDamage = Math.max(0, damage - defense);
        this.health -= effectiveDamage;
        if (this.health < 0) this.health = 0;
    }
}