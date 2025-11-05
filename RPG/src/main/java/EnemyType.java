
/**
 *
 *@author Sma1lo
 */

public enum EnemyType {
    ORC("Orc"),
    SKELETON("Skeleton"),
    GHOST("Ghost"),
    GOBLIN("Goblin");
    private final String enemyType;

    EnemyType(String enemyType) {
        this.enemyType = enemyType;
    }

    public String getType() {
        return enemyType;
    }
}