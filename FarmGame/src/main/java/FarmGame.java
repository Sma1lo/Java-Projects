import java.util.Scanner;

/**
 *
 *@author Sma1lo
 */

public class FarmGame {
    private Player player;
    private Scanner scanner;
    private int currentFieldIndex = 0;

    public static void main(String[] args) {
        FarmGame game = new FarmGame();
        game.start();
    }

    public void start() {
        scanner = new Scanner(System.in);
        player = new Player();

        System.out.println(ConsoleColor.GREEN + "Welcome to the farm!" + ConsoleColor.RESET);
        System.out.println("You have " + player.getCoins() + " coins and 1 field.");

        gameLoop();
    }

    private void gameLoop() {
        while (true) {
            try {
                displayMainMenu();
                String input = scanner.nextLine();

                if (input.trim().isEmpty()) {
                    continue;
                }

                int choice = Integer.parseInt(input);

                switch (choice) {
                    case 0 -> exitGame();
                    case 1 -> waterField();
                    case 2 -> fertilizeField();
                    case 3 -> showFieldInfo();
                    case 4 -> showInventory();
                    case 5 -> showShop();
                    case 6 -> selectField();
                    case 7 -> plantSeeds();
                    case 8 -> sellVegetables();
                    default -> showError("Invalid input");
                }

            } catch (NumberFormatException e) {
                showError("Please enter a number");
            } catch (Exception e) {
                showError("An error occurred: " + e.getMessage());
            }
        }
    }

    private void waterField() {
        Field currentField = getCurrentField();
        currentField.water();
        System.out.println(ConsoleColor.BLUE + "You watered the field." + ConsoleColor.RESET);
        waitForEnter();
    }

    private void fertilizeField() {
        Field currentField = getCurrentField();

        if (!currentField.hasCrops()) {
            System.out.println(ConsoleColor.RED + "Nothing is planted on the field!" + ConsoleColor.RESET);
            waitForEnter();
            return;
        }

        if (!currentField.isWatered()) {
            System.out.println(ConsoleColor.RED + "You need to water the field first!" + ConsoleColor.RESET);
            waitForEnter();
            return;
        }

        VegetableType currentCrop = currentField.getCropType();

        currentField.fertilize();

        if (currentField.isReadyForHarvest()) {
            int harvest = currentField.harvest();
            if (harvest > 0 && currentCrop != null) {
                String vegName = currentCrop.getName();
                player.addToInventory(vegName, harvest);

                System.out.println(ConsoleColor.GREEN + "Harvest collected! Gathered " + harvest + " " +
                        currentCrop.getRussianName() + ConsoleColor.RESET);

                System.out.println("Now you have " + player.getInventoryCount(vegName) + " " +
                        currentCrop.getRussianName());
            }
            currentField.reset();
        } else {
            System.out.println("Plant is growing... " + currentField.getGrowthStatus());
        }

        waitForEnter();
    }

    private void plantSeeds() {
        Field currentField = getCurrentField();

        if (currentField.hasCrops()) {
            System.out.println("Something is already growing on the field!");
            waitForEnter();
            return;
        }

        System.out.println(ConsoleColor.CYAN + "\n[SELECT SEEDS FOR PLANTING]" + ConsoleColor.RESET);

        boolean hasSeeds = false;
        for (VegetableType veg : VegetableManager.getAllVegetables()) {
            int seeds = player.getInventoryCount(veg.getName() + "_seeds");
            if (seeds > 0) {
                hasSeeds = true;
                System.out.println("- " + veg.getRussianName() + " (" + seeds + " seeds)");
            }
        }

        if (!hasSeeds) {
            System.out.println("You don't have any seeds! Buy them in the shop.");
            waitForEnter();
            return;
        }

        System.out.print("Choose a vegetable to plant: ");
        String choice = scanner.nextLine();

        VegetableType selectedVeg = VegetableManager.getByName(choice);
        if (selectedVeg != null) {
            if (player.getInventoryCount(selectedVeg.getName() + "_seeds") > 0) {
                if (currentField.plantCrop(selectedVeg)) {
                    player.removeFromInventory(selectedVeg.getName() + "_seeds", 1);
                    System.out.println("Planted " + selectedVeg.getRussianName());
                }
            } else {
                System.out.println("You don't have seeds " + selectedVeg.getRussianName());
            }
        } else {
            System.out.println("Unknown vegetable. Available: Corn, Cabbage, Potato, Wheat, Carrot, Tomato");
        }
        waitForEnter();
    }

    private void showFieldInfo() {
        System.out.println(ConsoleColor.CYAN + "\n[FIELD INFORMATION] " + (currentFieldIndex + 1) + ConsoleColor.RESET);
        getCurrentField().displayInfo();
        waitForEnter();
    }

    private void showInventory() {
        System.out.println(ConsoleColor.YELLOW + "\n[INVENTORY]" + ConsoleColor.RESET);
        System.out.println("Coins: " + player.getCoins());
        System.out.println("\nSeeds:");

        for (VegetableType veg : VegetableManager.getAllVegetables()) {
            int seeds = player.getInventoryCount(veg.getName() + "_seeds");
            if (seeds > 0) {
                System.out.println("  " + veg.getRussianName() + ": " + seeds + " pcs.");
            }
        }

        System.out.println("\nHarvest:");
        for (VegetableType veg : VegetableManager.getAllVegetables()) {
            int count = player.getInventoryCount(veg.getName());
            if (count > 0) {
                System.out.println("  " + veg.getRussianName() + ": " + count + " pcs.");
            }
        }
        waitForEnter();
    }

    private void sellVegetables() {
        System.out.println(ConsoleColor.PURPLE + "\n[SELL HARVEST]" + ConsoleColor.RESET);

        boolean hasVegetables = false;
        for (VegetableType veg : VegetableManager.getAllVegetables()) {
            int count = player.getInventoryCount(veg.getName());
            if (count > 0) {
                hasVegetables = true;
                System.out.println(veg.getRussianName() + ": " + count + " pcs. (price: " + veg.getSellPrice() + " coins/pc.)");
            }
        }

        if (!hasVegetables) {
            System.out.println("You don't have any vegetables to sell");
            waitForEnter();
            return;
        }

        System.out.print("Which vegetable to sell? ");
        String choice = scanner.nextLine();

        VegetableType selectedVeg = VegetableManager.getByName(choice);
        if (selectedVeg != null) {
            int count = player.getInventoryCount(selectedVeg.getName());
            if (count > 0) {
                System.out.print("How many to sell? (up to " + count + "): ");
                try {
                    int amount = Integer.parseInt(scanner.nextLine());
                    if (amount > 0 && amount <= count) {
                        player.sellVegetable(selectedVeg.getName(), amount);
                        System.out.println("Sold " + amount + " " + selectedVeg.getRussianName() +
                                " for " + (amount * selectedVeg.getSellPrice()) + " coins");
                    } else {
                        System.out.println("Invalid amount");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Enter a number");
                }
            } else {
                System.out.println("You don't have " + selectedVeg.getRussianName());
            }
        } else {
            System.out.println("Unknown vegetable");
        }
        waitForEnter();
    }

    private void showShop() {
        while (true) {
            displayShopMenu();
            String input = scanner.nextLine();

            if (input.trim().isEmpty()) continue;

            try {
                int choice = Integer.parseInt(input);

                switch (choice) {
                    case 0 -> {
                        return;
                    }
                    case 1 -> buySeeds();
                    case 2 -> buyNewField();
                    default -> showError("Invalid choice");
                }
            } catch (NumberFormatException e) {
                showError("Please enter a number");
            }
        }
    }

    private void buySeeds() {
        System.out.println(ConsoleColor.CYAN + "\n[BUY SEEDS]" + ConsoleColor.RESET);
        displaySeedPrices();

        System.out.print("Which seeds to buy? ");
        String choice = scanner.nextLine();

        VegetableType selectedVeg = VegetableManager.getByName(choice);
        if (selectedVeg != null) {
            System.out.print("How many seed packets to buy? (price: " + selectedVeg.getSeedPrice() + " coins/packet): ");
            try {
                int amount = Integer.parseInt(scanner.nextLine());
                if (amount > 0) {
                    int totalPrice = amount * selectedVeg.getSeedPrice();
                    if (player.spendCoins(totalPrice)) {
                        player.addToInventory(selectedVeg.getName() + "_seeds", amount);
                        System.out.println("Bought " + amount + " packets of seeds " + selectedVeg.getRussianName());
                    } else {
                        System.out.println("Not enough coins. Needed: " + totalPrice);
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Enter a number");
            }
        } else {
            System.out.println("Unknown vegetable");
        }
        waitForEnter();
    }

    private void buyNewField() {
        if (player.spendCoins(150)) {
            Field newField = new Field(50, 50);
            player.addField(newField);
            System.out.println("Bought a new field for 150 coins!");
        } else {
            System.out.println("Not enough coins. Need 150 coins.");
        }
        waitForEnter();
    }

    private void selectField() {
        System.out.println(ConsoleColor.PURPLE + "\n[SELECT FIELD]" + ConsoleColor.RESET);

        for (int i = 0; i < player.getFieldCount(); i++) {
            Field field = player.getFields().get(i);
            String status;
            if (field.hasCrops() && field.getCropType() != null) {
                status = field.getCropType().getRussianName() + " - " + field.getGrowthStatus();
            } else {
                status = "Empty";
            }
            System.out.println((i + 1) + ". Field " + (i + 1) + " - " + status);
        }

        System.out.print("Choose field (1-" + player.getFieldCount() + "): ");

        try {
            int choice = Integer.parseInt(scanner.nextLine()) - 1;
            if (choice >= 0 && choice < player.getFieldCount()) {
                currentFieldIndex = choice;
                System.out.println("Selected field " + (choice + 1));
            } else {
                showError("Invalid field number");
            }
        } catch (NumberFormatException e) {
            showError("Please enter a number");
        }
        waitForEnter();
    }

    private void displayAvailableSeeds() {
        for (VegetableType veg : VegetableManager.getAllVegetables()) {
            int seeds = player.getInventoryCount(veg.getName() + "_seeds");
            if (seeds > 0) {
                System.out.println("- " + veg.getRussianName() + " (" + seeds + " seeds)");
            }
        }
    }

    private void displaySeedPrices() {
        for (VegetableType veg : VegetableManager.getAllVegetables()) {
            System.out.println(veg.getRussianName() + ": " + veg.getSeedPrice() + " coins/packet");
        }
    }

    private Field getCurrentField() {
        return player.getFields().get(currentFieldIndex);
    }

    private void displayMainMenu() {
        Field currentField = getCurrentField();
        String fieldStatus;

        if (currentField.hasCrops() && currentField.getCropType() != null) {
            fieldStatus = currentField.getCropType().getRussianName() + " - " + currentField.getGrowthStatus();
        } else {
            fieldStatus = "Empty";
        }

        System.out.println(ConsoleColor.GREEN + "\n[=== FARM ===]" + ConsoleColor.RESET);
        System.out.println("Field " + (currentFieldIndex + 1) + ": " + fieldStatus);
        System.out.println("Coins: " + player.getCoins());
        System.out.println("[1] Water field");
        System.out.println("[2] Fertilize field");
        System.out.println("[3] Field information");
        System.out.println("[4] Inventory");
        System.out.println("[5] Shop");
        System.out.println("[6] Select field");
        System.out.println("[7] Plant seeds");
        System.out.println("[8] Sell harvest");
        System.out.println("[0] Exit");
        System.out.print("\nChoose action: ");
    }

    private void displayShopMenu() {
        System.out.println(ConsoleColor.PURPLE + "\n[=== SHOP ===]" + ConsoleColor.RESET);
        System.out.println("[1] Buy seeds");
        System.out.println("[2] Buy new field (150 coins)");
        System.out.println("[0] Back");
        System.out.print("\nChoose action: ");
    }

    private void waitForEnter() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private void showError(String message) {
        System.out.println(ConsoleColor.RED + "Error: " + message + ConsoleColor.RESET);
        waitForEnter();
    }

    private void exitGame() {
        System.out.println("Goodbye!");
        scanner.close();
        System.exit(0);
    }

}

