
/**
 *
 *@author Sma1lo
 */

public enum HeroType {
    MAGE("Mage"),
    PALADIN("Paladin"),
    HUNTER("Hunter");
    private final String heroType;

    HeroType(String heroType) {
        this.heroType = heroType;
    }

    public String getType() {
        return heroType;
    }
}