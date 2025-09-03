/**
 *
 *@author Sma1lo
 */

public class Field {
    private int height;
    private int width;
    private boolean watered;
    private int growthStage;
    private VegetableType cropType;
    private boolean readyForHarvest;
    private boolean hasCrops;

    public Field(int height, int width) {
        this.height = height;
        this.width = width;
        this.watered = false;
        this.growthStage = 0;
        this.cropType = null;
        this.readyForHarvest = false;
        this.hasCrops = false;
    }

    public boolean plantCrop(VegetableType cropType) {
        if (!hasCrops) {
            this.cropType = cropType;
            this.hasCrops = true;
            this.growthStage = 0;
            this.readyForHarvest = false;
            this.watered = false;
            return true;
        }
        return false;
    }

    public void water() {
        if (hasCrops) {
            this.watered = true;
        }
    }

    public void fertilize() {
        if (hasCrops && watered && !readyForHarvest) {
            growthStage++;
            watered = false;

            if (growthStage >= cropType.getGrowthStages()) {
                readyForHarvest = true;
            }
        }
    }

    public int harvest() {
        if (readyForHarvest && hasCrops && cropType != null) {
            int baseHarvest = (height * width) / 10;
            int randomBonus = (int) (Math.random() * 10) + 1;
            int harvestAmount = baseHarvest + randomBonus;

            reset();
            return harvestAmount;
        }
        return 0;
    }

    public void reset() {
        growthStage = 0;
        watered = false;
        cropType = null;
        readyForHarvest = false;
        hasCrops = false;
    }

    public String getGrowthStatus() {
        if (!hasCrops || cropType == null) return "Empty field";

        int maxStages = cropType.getGrowthStages();
        int progress = (growthStage * 100) / maxStages;

        if (progress == 0) return "Just planted";
        if (progress < 25) return "Sprout";
        if (progress < 50) return "Growing";
        if (progress < 75) return "Flowering";
        if (progress < 100) return "Ripening";
        return "Ready for harvest";
    }

    public void displayInfo() {
        System.out.println("Size: " + height + "x" + width);
        if (hasCrops && cropType != null) {
            System.out.println("Crop: " + cropType.getRussianName());
            System.out.println("Growth stage: " + getGrowthStatus() + " (" + growthStage + "/" + cropType.getGrowthStages() + ")");
            System.out.println("Watered: " + (watered ? "Yes" : "No"));
            System.out.println("Ready for harvest: " + (readyForHarvest ? "Yes" : "No"));
        } else {
            System.out.println("Status: Empty field");
        }
    }

    public boolean isWatered() {
        return watered;
    }

    public int getGrowthStage() {
        return growthStage;
    }

    public VegetableType getCropType() {
        return cropType;
    }

    public boolean isReadyForHarvest() {
        return readyForHarvest && hasCrops && cropType != null;
    }

    public boolean hasCrops() {
        return hasCrops && cropType != null;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

}
