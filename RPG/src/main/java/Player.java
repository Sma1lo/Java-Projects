
/**
 *
 *@author Sma1lo
 */

public class Player extends Character implements HasMana {
    private int level;
    private int experience;
    private int gold = 0;
    private int mana;
    private int maxMana;
    private HeroType heroType;
    private InventoryManager inventory;

    public Player(int id, String name, int damage, int health, int defense, int mana, int level, int experience, HeroType heroType) {
        super(id, name, damage, health, defense);
        this.mana = mana;
        this.maxMana = mana;
        this.level = level;
        this.experience = experience;
        this.heroType = heroType;
        this.inventory = new InventoryManager(Constants.INVENTORY_CAPACITY);
    }

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }

    public int getGold() {
        return gold;
    }

    public int getMana() {
        return mana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public HeroType getHeroType() {
        return heroType;
    }

    public InventoryManager getInventory() {
        return inventory;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public void setHeroType(HeroType heroType) {
        this.heroType = heroType;
    }

    public void basicAttack(Character target) {
        int damageDealt = this.damage;
        target.takeDamage(damageDealt);
        System.out.println(ConsoleColor.WHITE + getName() + " performs basic attack for " + damageDealt + " damage." + ConsoleColor.RESET);
    }

    public void useSkill(Character target) {
        if (mana < getManaCost()) {
            System.out.println(ConsoleColor.RED + "Not enough mana! Basic attack instead." + ConsoleColor.RESET);
            basicAttack(target);
            return;
        }
        mana -= getManaCost();
        int damageDealt = this.damage + getSpecialDamage();
        target.takeDamage(damageDealt);
        System.out.println(getSpecialAttackName());
        System.out.println(ConsoleColor.WHITE + getName() + " uses skill for " + damageDealt + " damage." + ConsoleColor.RESET);
    }

    private int getManaCost() {
        return switch (heroType) {
            case MAGE -> 10;
            case PALADIN -> 25;
            case HUNTER -> 15;
        };
    }

    private int getSpecialDamage() {
        return switch (heroType) {
            case MAGE -> 10;
            case PALADIN -> 5;
            case HUNTER -> 8;
        };
    }

    private String getSpecialAttackName() {
        return switch (heroType) {
            case MAGE -> ConsoleColor.RED + "Fireball!" + ConsoleColor.RESET;
            case PALADIN -> ConsoleColor.YELLOW + "Holy strike!" + ConsoleColor.RESET;
            case HUNTER -> ConsoleColor.GREEN + "Aimed shot!" + ConsoleColor.RESET;
        };
    }

    @Override
    public void attack(Character target) {
        basicAttack(target);
    }

    public void gainExperience(int amount) {
        this.experience += amount;
        System.out.println(ConsoleColor.WHITE + "Gained " + amount + ConsoleColor.CYAN + " experience!" + ConsoleColor.RESET);
        checkLevelUp();
    }

    public void gainGold(int amount) {
        this.gold += amount;
        System.out.println(ConsoleColor.WHITE + "Gained " + ConsoleColor.YELLOW + amount + " gold!" + ConsoleColor.RESET);
    }

    public void levelUp() {
        level++;
        maxMana += Constants.MANA_PER_LEVEL;
        mana = maxMana;
        damage += 2;
        health += 10;
        System.out.println(ConsoleColor.WHITE + "Level UP to " + level + "! Stats improved." + ConsoleColor.RESET);
    }

    private void checkLevelUp() {
        int expNeeded = level * Constants.BASE_EXP_PER_LEVEL;
        while (experience >= expNeeded) {
            experience -= expNeeded;
            levelUp();
            expNeeded = level * Constants.BASE_EXP_PER_LEVEL;
        }
    }
}