public class VegetableManager {
    public static final VegetableType CORN = new VegetableType("corn", "Corn", 5, 10, 5);
    public static final VegetableType CABBAGE = new VegetableType("cabbage", "Cabbage", 3, 7, 4);
    public static final VegetableType POTATO = new VegetableType("potato", "Potato", 2, 5, 3);
    public static final VegetableType WHEAT = new VegetableType("wheat", "Wheat", 4, 8, 4);
    public static final VegetableType CARROT = new VegetableType("carrot", "Carrot", 3, 6, 3);
    public static final VegetableType TOMATO = new VegetableType("tomato", "Tomato", 6, 12, 5);

    public static VegetableType[] getAllVegetables() {
        return new VegetableType[]{CORN, CABBAGE, POTATO, WHEAT, CARROT, TOMATO};
    }

    public static VegetableType getByName(String name) {
        for (VegetableType veg : getAllVegetables()) {
            if (veg.getName().equalsIgnoreCase(name) || veg.getRussianName().equalsIgnoreCase(name)) {
                return veg;
            }
        }
        return null;
    }
}