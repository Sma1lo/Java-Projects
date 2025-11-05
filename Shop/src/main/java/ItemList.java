/**
 *
 *@author Sma1lo
 */

public enum ItemList {
    APPLE("Apple", 10, "Fresh red apple"),
    BANANA("Banana", 15, "Ripe yellow banana"),
    ORANGE("Orange", 20, "Juicy orange"),
    GRAPES("Grapes", 25, "Sweet grapes"),
    WATERMELON("Watermelon", 50, "Large juicy watermelon");

    private final String name;
    private final int price;
    private final String description;

    ItemList(String name, int price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}