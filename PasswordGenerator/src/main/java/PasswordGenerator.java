import java.security.SecureRandom;
import java.util.Scanner;

public class PasswordGenerator {

    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                showMenu();
                int input = scanner.nextInt();
                switch (input) {
                    case 1:
                        System.out.print("Enter the desired password length: ");
                        int passwordLength = getPasswordLength(scanner);

                        System.out.print("Include special characters? (yes/no): ");
                        boolean includeSpecialCharacters = scanner.next().equalsIgnoreCase("yes");

                        String password = generatePassword(passwordLength, includeSpecialCharacters);
                        System.out.println("Generated password: " + password);
                        break;
                    case 0:
                        System.out.print("Exiting...");
                        break;
                }
            }
            catch (Exception e) {
                System.out.println("Invalid input.");
                scanner.nextLine();
            }
        }
    }

    public static void showMenu() {
        System.out.println("[1] Generate Password");
        System.out.println("[0] Exit\n");
        System.out.print("Enter number: ");
    }

    private static int getPasswordLength(Scanner scanner) {
        while (true) {
            if (scanner.hasNextInt()) {
                int length = scanner.nextInt();
                if (length > 0) {
                    return length;
                } else {
                    System.out.print("Password length must be a positive number. Please try again: ");
                }
            } else {
                System.out.print("Please enter an integer. Please try again: ");
                scanner.next();
            }
        }
    }

    public static String generatePassword(int length, boolean includeSpecialCharacters) {
        SecureRandom random = new SecureRandom();
        StringBuilder passwordCharacters = new StringBuilder(UPPERCASE + LOWERCASE + DIGITS);

        if (includeSpecialCharacters) {
            passwordCharacters.append(SPECIAL_CHARACTERS);
        }

        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(passwordCharacters.length());
            password.append(passwordCharacters.charAt(index));
        }

        return password.toString();
    }
}