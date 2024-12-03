import java.security.SecureRandom;
import java.util.Scanner;

public class PasswordGenerator {

    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the desired password length: ");
            int passwordLength = getPasswordLength(scanner);
            
            System.out.print("Include special characters? (yes/no): ");
            boolean includeSpecialCharacters = scanner.next().equalsIgnoreCase("yes");

            String password = generatePassword(passwordLength, includeSpecialCharacters);
            System.out.println("Generated password: " + password);
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
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
