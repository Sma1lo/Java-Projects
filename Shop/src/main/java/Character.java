/**
 *
 *@author Sma1lo
 */

public abstract class Character extends Entity {
    protected int money;
    protected Inventory inventory;

    public Character(String name, int money) {
        super(name);
        this.money = money;
        this.inventory = new Inventory();
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Inventory getInventory() {
        return inventory;
    }
}