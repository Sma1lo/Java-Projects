
/**
 *
 *@author Sma1lo
 */

public class Quest {
    private String description;
    private int requiredKills;
    private EnemyType targetType;
    private int expReward;
    private int goldReward;
    private Item itemReward;
    private int progress = 0;

    public Quest(String description, int requiredKills, EnemyType targetType, int expReward, int goldReward, Item itemReward) {
        this.description = description;
        this.requiredKills = requiredKills;
        this.targetType = targetType;
        this.expReward = expReward;
        this.goldReward = goldReward;
        this.itemReward = itemReward;
    }

    public void progress(EnemyType killedType) {
        if (killedType == targetType) {
            progress++;
        }
    }

    public boolean isCompleted() {
        return progress >= requiredKills;
    }

    public String getDescription() {
        return description;
    }

    public int getRequiredKills() {
        return requiredKills;
    }

    public int getProgress() {
        return progress;
    }

    public int getExpReward() {
        return expReward;
    }

    public int getGoldReward() {
        return goldReward;
    }

    public Item getItemReward() {
        return itemReward;
    }
}