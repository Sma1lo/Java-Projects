
/**
 *
 *@author Sma1lo
 */

public class Enemy extends Character {
    private EnemyType enemyType;
    private int experienceReward;
    private int goldReward;

    public Enemy(int id, String name, int damage, int health, int defense, EnemyType enemyType, int experienceReward, int goldReward) {
        super(id, name, damage, health, defense);
        this.enemyType = enemyType;
        this.experienceReward = experienceReward;
        this.goldReward = goldReward;
    }

    public EnemyType getEnemyType() {
        return enemyType;
    }

    public int getExperienceReward() {
        return experienceReward;
    }

    public int getGoldReward() {
        return goldReward;
    }

    public void setEnemyType(EnemyType enemyType) {
        this.enemyType = enemyType;
    }

    public void setExperienceReward(int experienceReward) {
        this.experienceReward = experienceReward;
    }

    public void setGoldReward(int goldReward) {
        this.goldReward = goldReward;
    }

    @Override
    public void attack(Character target) {
        int damageDealt = this.damage;
        target.takeDamage(damageDealt);
        System.out.println(ConsoleColor.WHITE + getName() + " attacks and deals " + damageDealt + " damage." + ConsoleColor.RESET);
    }
}