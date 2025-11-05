import java.util.Map;

/**
 *
 *@author Sma1lo
 */

public interface HasStats {
    Map<String, Integer> getAllStatBonuses();

    Integer getStatBonus(String stat);

    void addStatBonus(String stat, int bonus);
}