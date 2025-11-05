import java.util.*;
import java.io.*;

/**
 *
 *@author Sma1lo
 */

public class GameLogic {
    static Random random = new Random();
    static Scanner scanner = new Scanner(System.in);
    static int randomId = random.nextInt(10000);
    private static Player player;
    private static boolean gameRunning = true;
    static ItemManager itemManager = new ItemManager();
    private static Shop shop = new Shop();
    private static List<Quest> quests = new ArrayList<>();

    public static void startGame() {
        System.out.println(ConsoleColor.CYAN + "=== " + ConsoleColor.PURPLE + "WELCOME TO RPG GAME" + ConsoleColor.CYAN + " ===" + ConsoleColor.RESET);
        initializeQuests();
        loadOrCreatePlayer();
        while (gameRunning && player.isAlive()) {
            showMainMenu();
        }
        if (!player.isAlive()) {
            System.out.println(ConsoleColor.RED + "Game Over! Your hero has fallen..." + ConsoleColor.RESET);
        }
        scanner.close();
    }

    private static void initializeQuests() {
        quests.add(new Quest("Slay 3 Goblins", 3, EnemyType.GOBLIN, 100, 50, itemManager.createHealthPotion("Health Potion", "Restores 30 HP", 25, 1, 30, 1)));
        quests.add(new Quest("Defeat the Orc Brute", 1, EnemyType.ORC, 200, 100, itemManager.createWeapon("Steel Sword", "A sturdy steel sword", 100, 6, 12, 3)));
    }

    private static void loadOrCreatePlayer() {
        System.out.print(ConsoleColor.WHITE + "Load saved game? (y/n): " + ConsoleColor.RESET);
        String choice = scanner.nextLine().toLowerCase();
        if (choice.equals("y")) {
            loadPlayer();
        } else {
            createPlayer();
            initializeStartingItems();
        }
    }

    private static void savePlayer() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("player_save.txt"))) {
            writer.println(player.getName());
            writer.println(player.getHeroType().name());
            writer.println(player.getDamage());
            writer.println(player.getHealth());
            writer.println(player.getDefense());
            writer.println(player.getMana());
            writer.println(player.getMaxMana());
            writer.println(player.getLevel());
            writer.println(player.getExperience());
            writer.println(player.getGold());

            writer.println("EquippedItems:");
            for (Map.Entry<ItemType, Item> entry : player.getInventory().getEquippedItems().entrySet()) {
                writer.println(entry.getValue().getId());
            }

            writer.println("Inventory:");
            for (Item item : player.getInventory().getItems()) {
                writer.println(item.getId());
            }

            System.out.println(ConsoleColor.GREEN + "Game saved!" + ConsoleColor.RESET);
        } catch (IOException e) {
            System.out.println(ConsoleColor.RED + "Error saving game." + ConsoleColor.RESET);
        }
    }

    private static void loadPlayer() {
        try (Scanner fileScanner = new Scanner(new File("player_save.txt"))) {
            String name = fileScanner.nextLine();
            HeroType heroType = HeroType.valueOf(fileScanner.nextLine());
            int damage = Integer.parseInt(fileScanner.nextLine());
            int health = Integer.parseInt(fileScanner.nextLine());
            int defense = Integer.parseInt(fileScanner.nextLine());
            int mana = Integer.parseInt(fileScanner.nextLine());
            int maxMana = Integer.parseInt(fileScanner.nextLine());
            int level = Integer.parseInt(fileScanner.nextLine());
            int experience = Integer.parseInt(fileScanner.nextLine());
            int gold = Integer.parseInt(fileScanner.nextLine());

            player = new Player(randomId, name, damage, health, defense, mana, level, experience, heroType);
            player.setMaxMana(maxMana);
            player.setGold(gold);

            if (fileScanner.hasNextLine()) fileScanner.nextLine();

            if (fileScanner.hasNextLine() && fileScanner.nextLine().equals("EquippedItems:")) {
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    if (line.equals("Inventory:")) break;

                    int itemId = Integer.parseInt(line);
                    Item item = itemManager.getItemById(itemId);
                    if (item != null) {
                        player.getInventory().equipItem(item, player);
                    }
                }
            }

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.startsWith("Inventory items:")) continue;
                if (line.isEmpty()) break;

                int itemId = Integer.parseInt(line);
                Item item = itemManager.getItemById(itemId);
                if (item != null) {
                    player.getInventory().addItem(item);
                }
            }

            System.out.println(ConsoleColor.GREEN + "Game loaded!" + ConsoleColor.RESET);
        } catch (FileNotFoundException e) {
            System.out.println(ConsoleColor.RED + "No save found. Creating new player." + ConsoleColor.RESET);
            createPlayer();
            initializeStartingItems();
        }
    }

    private static void initializeStartingItems() {
        switch (player.getHeroType()) {
            case MAGE:
                player.getInventory().addItem(itemManager.createWeapon("Basic Staff", "A simple wooden staff", 30, 3, 5, 1));
                player.getInventory().addItem(itemManager.createHealthPotion("Health Potion", "Restores 30 HP", 25, 1, 30, 1));
                break;
            case PALADIN:
                player.getInventory().addItem(itemManager.createWeapon("Training Sword", "A basic training sword", 40, 5, 8, 1));
                player.getInventory().addItem(itemManager.createArmor("Leather Armor", "Basic leather protection", 50, 8, 3, 1));
                break;
            case HUNTER:
                player.getInventory().addItem(itemManager.createWeapon("Hunting Bow", "A reliable hunting bow", 35, 4, 7, 1));
                player.getInventory().addItem(itemManager.createHealthPotion("Health Potion", "Restores 30 HP", 25, 1, 30, 1));
                break;
        }
    }

    private static void handleMenuChoice(int choice) {
        switch (choice) {
            case 1 -> startCombat();
            case 2 -> showInventory();
            case 3 -> showPlayerStats();
            case 4 -> showShop();
            case 5 -> showQuests();
            case 6 -> savePlayer();
            case 7 -> {
                gameRunning = false;
                System.out.println(ConsoleColor.WHITE + "Thanks for playing!" + ConsoleColor.RESET);
            }
            default -> System.out.println(ConsoleColor.RED + "Invalid choice!" + ConsoleColor.RESET);
        }
    }

    public static void showMainMenu() {
        System.out.println(ConsoleColor.CYAN + "\n=== " + ConsoleColor.PURPLE + "MAIN MENU" + ConsoleColor.CYAN + " ===" + ConsoleColor.RESET);
        System.out.println(ConsoleColor.WHITE + "1. Fight enemy");
        System.out.println("2. Inventory");
        System.out.println("3. Player stats");
        System.out.println("4. Shop");
        System.out.println("5. Quests");
        System.out.println("6. Save game");
        System.out.println("7. Exit game");
        System.out.print("\nChoose option: " + ConsoleColor.RESET);
        int choice = scanner.nextInt();
        refreshScreen();
        handleMenuChoice(choice);
    }

    public static void createPlayer() {
        scanner.nextLine();
        System.out.println(ConsoleColor.CYAN + "\n=== " + ConsoleColor.PURPLE + "CREATE YOUR HERO" + ConsoleColor.CYAN + " ===" + ConsoleColor.RESET);
        System.out.print(ConsoleColor.WHITE + "\nEnter your hero name: " + ConsoleColor.RESET);
        String name = scanner.nextLine();
        System.out.println(ConsoleColor.WHITE + "\nChoose your class: " + ConsoleColor.RESET);
        System.out.println(ConsoleColor.BLUE + "\n1. Mage" + ConsoleColor.RESET);
        System.out.println(ConsoleColor.YELLOW + "2. Paladin" + ConsoleColor.RESET);
        System.out.println(ConsoleColor.RED + "3. Hunter" + ConsoleColor.RESET);
        System.out.print(ConsoleColor.WHITE + "\n Choose hero class: " + ConsoleColor.RESET);
        int classChoice = scanner.nextInt();
        refreshScreen();
        HeroType heroType = getHeroType(classChoice);
        player = new Player(randomId, name, getBaseDamage(heroType), getBaseHealth(heroType), getBaseDefense(heroType), getBaseMana(heroType), 1, 0, heroType);
        System.out.println(ConsoleColor.WHITE + "\nHero created: " + name + " the " + heroType.getType() + ConsoleColor.RESET);
    }

    public static HeroType getHeroType(int choice) {
        return switch (choice) {
            case 1 -> HeroType.MAGE;
            case 2 -> HeroType.PALADIN;
            case 3 -> HeroType.HUNTER;
            default -> HeroType.PALADIN;
        };
    }

    private static int getBaseDamage(HeroType heroType) {
        return switch (heroType) {
            case MAGE -> 8;
            case PALADIN -> 12;
            case HUNTER -> 10;
        };
    }

    private static int getBaseHealth(HeroType heroType) {
        return switch (heroType) {
            case MAGE -> 80;
            case PALADIN -> 120;
            case HUNTER -> 100;
        };
    }

    private static int getBaseDefense(HeroType heroType) {
        return switch (heroType) {
            case MAGE -> 5;
            case PALADIN -> 10;
            case HUNTER -> 7;
        };
    }

    private static int getBaseMana(HeroType heroType) {
        return switch (heroType) {
            case MAGE -> 300;
            case PALADIN -> 100;
            case HUNTER -> 50;
        };
    }

    public static void showInventory() {
        boolean inInventory = true;
        while (inInventory) {
            System.out.println("\n" + "=".repeat(40));
            System.out.println(ConsoleColor.CYAN + "\n=== " + ConsoleColor.PURPLE + "INVENTORY" + ConsoleColor.CYAN + " ===" + ConsoleColor.RESET);
            System.out.println("=".repeat(40));
            System.out.println(ConsoleColor.YELLOW + "Gold: " + ConsoleColor.RESET + player.getGold());
            player.getInventory().displayInventory();
            System.out.println(ConsoleColor.CYAN + "\n=== " + ConsoleColor.PURPLE + "ACTIONS" + ConsoleColor.CYAN + " ===" + ConsoleColor.RESET);
            System.out.println(ConsoleColor.WHITE + "1. Use item");
            System.out.println("2. Equip item");
            System.out.println("3. Unequip item");
            System.out.println("4. Sell item");
            System.out.println("5. Item info");
            System.out.println("6. Back to main menu");
            System.out.print("\nChoose option: " + ConsoleColor.RESET);
            try {
                int choice = scanner.nextInt();
                refreshScreen();
                switch (choice) {
                    case 1 -> useItemMenu();
                    case 2 -> equipItemMenu();
                    case 3 -> unequipItemMenu();
                    case 4 -> sellItemMenu();
                    case 5 -> showItemInfoMenu();
                    case 6 -> inInventory = false;
                    default ->
                            System.out.println(ConsoleColor.RED + "Invalid choice! Please enter 1-6." + ConsoleColor.RESET);
                }
            } catch (InputMismatchException e) {
                System.out.println(ConsoleColor.RED + "Please enter a valid number." + ConsoleColor.RESET);
                scanner.next();
            }
        }
    }

    private static void useItemMenu() {
        List<Item> items = player.getInventory().getItems();
        if (items.isEmpty()) {
            System.out.println(ConsoleColor.RED + "Your inventory is empty." + ConsoleColor.RESET);
            return;
        }
        System.out.println(ConsoleColor.CYAN + "\n=== " + ConsoleColor.PURPLE + "USE ITEM" + ConsoleColor.CYAN + " ===" + ConsoleColor.RESET);
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            String type = item.isConsumable() ? ConsoleColor.BLUE + "[USE]" + ConsoleColor.RESET : ConsoleColor.GREEN + "[EQUIP]" + ConsoleColor.RESET;
            System.out.println((i + 1) + ". " + item.getName() + " " + type);
        }
        System.out.println(ConsoleColor.WHITE + "0. Back" + ConsoleColor.RESET);
        System.out.print(ConsoleColor.WHITE + "Choose item to use: " + ConsoleColor.RESET);
        int choice = scanner.nextInt();
        refreshScreen();
        if (choice == 0) return;
        if (choice > 0 && choice <= items.size()) {
            player.getInventory().useItem(choice - 1, player);
        } else {
            System.out.println(ConsoleColor.RED + "Invalid item number." + ConsoleColor.RESET);
        }
    }

    private static void equipItemMenu() {
        List<Item> equippableItems = player.getInventory().getItems().stream()
                .filter(Item::isEquippable)
                .toList();
        if (equippableItems.isEmpty()) {
            System.out.println(ConsoleColor.RED + "No equippable items in inventory." + ConsoleColor.RESET);
            return;
        }
        System.out.println(ConsoleColor.CYAN + "\n=== " + ConsoleColor.PURPLE + "EQUIP ITEM" + ConsoleColor.CYAN + " ===" + ConsoleColor.RESET);
        for (int i = 0; i < equippableItems.size(); i++) {
            Item item = equippableItems.get(i);
            System.out.println((i + 1) + ". " + item.getName() + " (" + item.getItemType() + ")");
        }
        System.out.println(ConsoleColor.WHITE + "0. Back" + ConsoleColor.RESET);
        System.out.print(ConsoleColor.WHITE + "Choose item to equip: " + ConsoleColor.RESET);
        int choice = scanner.nextInt();
        refreshScreen();
        if (choice == 0) return;
        if (choice > 0 && choice <= equippableItems.size()) {
            Item itemToEquip = equippableItems.get(choice - 1);
            player.getInventory().equipItem(itemToEquip, player);
        } else {
            System.out.println(ConsoleColor.RED + "Invalid item number." + ConsoleColor.RESET);
        }
    }

    private static void unequipItemMenu() {
        Map<ItemType, Item> equippedItems = player.getInventory().getEquippedItems();
        if (equippedItems.isEmpty()) {
            System.out.println(ConsoleColor.RED + "No items equipped." + ConsoleColor.RESET);
            return;
        }
        System.out.println(ConsoleColor.CYAN + "\n=== " + ConsoleColor.PURPLE + "UNEQUIP ITEM" + ConsoleColor.CYAN + " ===" + ConsoleColor.RESET);
        List<ItemType> equippedTypes = new ArrayList<>(equippedItems.keySet());
        for (int i = 0; i < equippedTypes.size(); i++) {
            ItemType type = equippedTypes.get(i);
            Item item = equippedItems.get(type);
            System.out.println((i + 1) + ". " + item.getName() + " (" + type + ")");
        }
        System.out.println(ConsoleColor.WHITE + "0. Back" + ConsoleColor.RESET);
        System.out.print(ConsoleColor.WHITE + "Choose item to unequip: " + ConsoleColor.RESET);
        int choice = scanner.nextInt();
        refreshScreen();
        if (choice == 0) return;
        if (choice > 0 && choice <= equippedTypes.size()) {
            ItemType typeToUnequip = equippedTypes.get(choice - 1);
            player.getInventory().unequipItem(typeToUnequip, player);
        } else {
            System.out.println(ConsoleColor.RED + "Invalid item number." + ConsoleColor.RESET);
        }
    }

    private static void sellItemMenu() {
        List<Item> items = player.getInventory().getItems();
        if (items.isEmpty()) {
            System.out.println(ConsoleColor.RED + "Your inventory is empty." + ConsoleColor.RESET);
            return;
        }
        System.out.println(ConsoleColor.CYAN + "\n=== " + ConsoleColor.PURPLE + "SELL ITEM" + ConsoleColor.CYAN + " ===" + ConsoleColor.RESET);
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            int sellPrice = item.getValue() / 2;
            String equipped = player.getInventory().getEquippedItems().containsValue(item) ? " [EQUIPPED]" : "";
            System.out.println((i + 1) + ". " + item.getName() + " - " + sellPrice + " gold" + equipped);
        }
        System.out.println(ConsoleColor.WHITE + "0. Back" + ConsoleColor.RESET);
        System.out.print(ConsoleColor.WHITE + "Choose item to sell: " + ConsoleColor.RESET);
        int choice = scanner.nextInt();
        refreshScreen();
        if (choice == 0) return;
        if (choice > 0 && choice <= items.size()) {
            System.out.print(ConsoleColor.WHITE + "Are you sure you want to sell this item? (y/n): " + ConsoleColor.RESET);
            String confirm = scanner.next().toLowerCase();
            if (confirm.equals("y") || confirm.equals("yes")) {
                player.getInventory().sellItem(choice - 1, player);
            } else {
                System.out.println(ConsoleColor.WHITE + "Sale cancelled." + ConsoleColor.RESET);
            }
        } else {
            System.out.println(ConsoleColor.RED + "Invalid item number." + ConsoleColor.RESET);
        }
    }

    private static void showItemInfoMenu() {
        List<Item> items = player.getInventory().getItems();
        if (items.isEmpty()) {
            System.out.println(ConsoleColor.RED + "Your inventory is empty." + ConsoleColor.RESET);
            return;
        }
        System.out.println(ConsoleColor.CYAN + "\n=== " + ConsoleColor.PURPLE + "ITEM INFO" + ConsoleColor.CYAN + " ===" + ConsoleColor.RESET);
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            System.out.println((i + 1) + ". " + item.getName());
        }
        System.out.println(ConsoleColor.WHITE + "0. Back" + ConsoleColor.RESET);
        System.out.print("Choose item to view info: ");
        int choice = scanner.nextInt();
        refreshScreen();
        if (choice == 0) return;
        if (choice > 0 && choice <= items.size()) {
            Item item = items.get(choice - 1);
            System.out.println("\n" + "=".repeat(30));
            System.out.println(item.getFullInfo());
            System.out.println("=".repeat(30));
        } else {
            System.out.println(ConsoleColor.RED + "Invalid item number." + ConsoleColor.RESET);
        }
    }

    public static void showPlayerStats() {
        if (player == null) {
            System.out.println(ConsoleColor.RED + "Player not initialized." + ConsoleColor.RESET);
            return;
        }
        System.out.println(ConsoleColor.CYAN + "\n=== " + ConsoleColor.PURPLE + "PLAYER STATS" + ConsoleColor.CYAN + " ===" + ConsoleColor.RESET);
        System.out.println(ConsoleColor.WHITE + "Name: " + player.getName());
        System.out.println("Class: " + player.getHeroType().getType());
        System.out.println("Level: " + player.getLevel());
        System.out.println("Health: " + player.getHealth());
        System.out.println("Defense: " + player.getDefense());
        System.out.println("Mana: " + player.getMana() + "/" + player.getMaxMana());
        System.out.println("Damage: " + player.getDamage());
        System.out.println("Experience: " + player.getExperience());
        System.out.println("Gold: " + player.getGold() + ConsoleColor.RESET);
    }

    public static void startCombat() {
        Enemy enemy = createRandomEnemy();
        CombatSystem.startBattle(player, enemy);
        checkQuestsAfterBattle(enemy);
    }

    private static void checkQuestsAfterBattle(Enemy enemy) {
        Iterator<Quest> iterator = quests.iterator();
        while (iterator.hasNext()) {
            Quest quest = iterator.next();
            quest.progress(enemy.getEnemyType());
            if (quest.isCompleted()) {
                System.out.println(ConsoleColor.GREEN + "Quest completed: " + quest.getDescription() + ConsoleColor.RESET);
                player.gainExperience(quest.getExpReward());
                player.gainGold(quest.getGoldReward());
                player.getInventory().addItem(quest.getItemReward());
                iterator.remove();
            }
        }
    }

    private static Enemy createRandomEnemy() {
        int enemyId = random.nextInt(1000);
        return switch (random.nextInt(3)) {
            case 0 -> new Enemy(enemyId, "Goblin Scout", 8, 30, 2, EnemyType.GOBLIN, 10, 3);
            case 1 -> new Enemy(enemyId, "Skeleton Warrior", 12, 40, 4, EnemyType.SKELETON, 15, 10);
            case 2 -> new Enemy(enemyId, "Orc Brute", 15, 50, 6, EnemyType.ORC, 20, 15);
            default -> new Enemy(enemyId, "Wild Beast", 10, 35, 3, EnemyType.GOBLIN, 12, 5);
        };
    }

    public static void useItemInBattle() {
        List<Item> usableItems = player.getInventory().getItems().stream()
                .filter(item -> item.isConsumable() || item instanceof Scroll)
                .toList();

        if (usableItems.isEmpty()) {
            System.out.println(ConsoleColor.RED + "No usable items in inventory." + ConsoleColor.RESET);
            return;
        }

        System.out.println(ConsoleColor.CYAN + "\n=== " + ConsoleColor.PURPLE + "USE ITEM IN BATTLE" + ConsoleColor.CYAN + " ===" + ConsoleColor.RESET);
        for (int i = 0; i < usableItems.size(); i++) {
            Item item = usableItems.get(i);
            System.out.println((i + 1) + ". " + item.getName());
        }
        System.out.println(ConsoleColor.WHITE + "0. Cancel" + ConsoleColor.RESET);
        System.out.print(ConsoleColor.WHITE + "Choose item to use: " + ConsoleColor.RESET);

        try {
            int choice = scanner.nextInt();
            refreshScreen();
            if (choice == 0) return;
            if (choice > 0 && choice <= usableItems.size()) {
                Item item = usableItems.get(choice - 1);
                player.getInventory().useItem(item, player);
            } else {
                System.out.println(ConsoleColor.RED + "Invalid item number. Lost turn." + ConsoleColor.RESET);
            }
        } catch (InputMismatchException e) {
            System.out.println(ConsoleColor.RED + "Please enter a valid number. Lost turn." + ConsoleColor.RESET);
            scanner.next();
        }
    }

    static void refreshScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static Player getPlayer() {
        return player;
    }

    public static void showShop() {
        shop.showShopMenu(player);
    }

    public static void showQuests() {
        System.out.println(ConsoleColor.CYAN + "\n=== " + ConsoleColor.PURPLE + "QUESTS" + ConsoleColor.CYAN + " ===" + ConsoleColor.RESET);
        if (quests.isEmpty()) {
            System.out.println(ConsoleColor.RED + "No active quests." + ConsoleColor.RESET);
        } else {
            for (Quest quest : quests) {
                System.out.println(quest.getDescription() + " - Progress: " + quest.getProgress() + "/" + quest.getRequiredKills());
            }
        }
    }
}