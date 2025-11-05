
/**
 *
 *@author Sma1lo
 */


public class CombatSystem {
    public static void startBattle(Player player, Enemy enemy) {
        System.out.println(ConsoleColor.RED + "\nPREPARING FOR BATTLE..." + ConsoleColor.RESET);
        System.out.println(ConsoleColor.WHITE + "A wild " + enemy.getName() + " appears." + ConsoleColor.RESET);
        int round = 1;
        while (player.isAlive() && enemy.isAlive()) {
            System.out.println(ConsoleColor.RED + "\n--- Round " + round + " ---" + ConsoleColor.RESET);
            System.out.println(ConsoleColor.WHITE + player.getName() + " HP: " + player.getHealth() + " Mana: " + player.getMana() + "/" + player.getMaxMana() + ConsoleColor.RESET);
            System.out.println(ConsoleColor.WHITE + enemy.getName() + " HP: " + enemy.getHealth() + ConsoleColor.RESET);
            System.out.println(ConsoleColor.WHITE + "\nYour turn:\n");
            System.out.println("1. Basic Attack");
            System.out.println("2. Use Skill");
            System.out.println("3. Use Item");
            System.out.println("4. Try to Escape");
            System.out.print("Choose action: " + ConsoleColor.RESET);
            int choice = GameLogic.scanner.nextInt();
            GameLogic.refreshScreen();
            switch (choice) {
                case 1 -> player.basicAttack(enemy);
                case 2 -> player.useSkill(enemy);
                case 3 -> GameLogic.useItemInBattle();
                case 4 -> {
                    if (Math.random() < 0.5) {
                        System.out.println(ConsoleColor.GREEN + "You escaped successfully!" + ConsoleColor.RESET);
                        return;
                    } else {
                        System.out.println(ConsoleColor.RED + "Escape failed!" + ConsoleColor.RESET);
                    }
                }
                default -> System.out.println(ConsoleColor.RED + "Invalid choice! Lost turn." + ConsoleColor.RESET);
            }
            if (enemy.isAlive()) {
                enemy.attack(player);
            }
            round++;
        }
        if (player.isAlive()) {
            System.out.println(ConsoleColor.GREEN + "\nVICTORY! You defeated " + enemy.getName() + ConsoleColor.RESET);
            player.gainExperience(enemy.getExperienceReward());
            player.gainGold(enemy.getGoldReward());
            if (Math.random() < Constants.LOOT_DROP_CHANCE) {
                Item loot = GameLogic.itemManager.getRandomItem();
                if (player.getInventory().addItem(loot)) {
                    System.out.println(ConsoleColor.WHITE + "Looted: " + loot.getName() + "!" + ConsoleColor.RESET);
                }
            }
        } else {
            System.out.println(ConsoleColor.RED + "\nDEFEAT... " + enemy.getName() + " was too strong!" + ConsoleColor.RESET);
        }
    }
}