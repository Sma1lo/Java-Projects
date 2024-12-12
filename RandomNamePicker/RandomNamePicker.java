import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class RandomNamePicker {
    private static ArrayList<String> boyNames = new ArrayList<>();
    private static ArrayList<String> girlNames = new ArrayList<>();
    private static Random random = new Random();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args){
      initializeNames();

        while (true) {
            System.out.println("1. Get a random boy's name");
            System.out.println("2. Get a random girl's name");
            System.out.println("3. Add a name");
            System.out.println("4. Remove a name");
            System.out.println("0. Exit\n");
            System.out.print("Enter number: ");
            int choice = scanner.nextInt();
            scanner.nextLine();     

            switch (choice) {
                case 1:
                    System.out.println("Random boy's name: " + getRandomName(boyNames) + "\n");
                    break;
                case 2:
                    System.out.println("Random girl's name: " + getRandomName(girlNames) + "\n");
                    break;
                case 3:
                    addName();
                    break;
                case 4:
                    removeName();
                    break;
                case 0:
                    System.exit(0);
                    return;
                default:
                    System.out.println("Invalid choice, please try again.\n");
            }
        }
    }

    private static void initializeNames() {
        boyNames.add("Bob");
        boyNames.add("Alex");
        boyNames.add("Mike");

        girlNames.add("Anna");
        girlNames.add("Maria");
        girlNames.add("Kate");
    }

    private static String getRandomName(ArrayList<String> names) {
        if (names.isEmpty()) {
            return "No available names.\n";
        }
        int index = random.nextInt(names.size());
        return names.get(index);
    }

    private static void addName() {
        System.out.print("Enter a name: ");
        String name = scanner.nextLine();

        System.out.println("Enter 'boy' for a boy's name or 'girl' for a girl's name: ");
        String gender = scanner.nextLine();

        if (gender.equalsIgnoreCase("boy")) {
            boyNames.add(name);
            System.out.println("Name added to the boys' list.\n");
        } else if (gender.equalsIgnoreCase("girl")) {
            girlNames.add(name);

            System.out.println("Name added to the girls' list.\n");
        } else {
            System.out.println("Invalid input. Name not added.\n");
        }
    }

    private static void removeName() {
        System.out.print("Enter a name to remove: ");
        String name = scanner.nextLine();
        if (boyNames.remove(name)) {

            System.out.println("Name removed from the boys' list.\n");
        } else if (girlNames.remove(name)) {

            System.out.println("Name removed from the girls' list.\n");
        } else {
            System.out.println("Name not found in the lists.\n");
        }
    }
}
