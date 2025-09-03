public class VegetableType {
    private String name;
    private int seedPrice;
    private int sellPrice;
    private int growthStages;
    private String russianName;

    public VegetableType(String name, String russianName, int seedPrice, int sellPrice, int growthStages) {
        this.name = name;
        this.russianName = russianName;
        this.seedPrice = seedPrice;
        this.sellPrice = sellPrice;
        this.growthStages = growthStages;
    }

    public String getName() {
        return name;
    }

    public String getRussianName() {
        return russianName;
    }

    public int getSeedPrice() {
        return seedPrice;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public int getGrowthStages() {
        return growthStages;
    }
}