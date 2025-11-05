
/**
 *
 *@author Sma1lo
 */

public enum ItemType {
    WEAPON("Weapon"),
    ARMOR("Armor"),
    POTION("Potion"),
    SCROLL("Scroll"),
    MATERIAL("Material"),
    QUEST("Quest Item"),
    MISC("Miscellaneous");
    private final String displayName;

    ItemType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}