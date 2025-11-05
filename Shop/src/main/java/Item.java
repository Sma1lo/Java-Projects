/**
 *
 *@author Sma1lo
 */

public abstract class Item extends Entity {
    String description;

    public Item(String name, String description) {
        super(name);
        this.description = description;
    }
}
