import java.util.HashMap;
import java.util.Scanner;

public class MorseCodeLogic {

    public static void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Convert text to Morse code");
            System.out.println("2. Translate Morse code into text");
            System.out.println("0. Exit\n");
            System.out.print("Enter number: ");
            int choice = scanner.nextInt();
            scanner.nextLine();


            switch (choice) {
                case 1:
                    System.out.print("Enter text to be translated into Morse code: ");
                    String inputText = scanner.nextLine().toUpperCase();


                    String morseCode = convertTextToMorse(inputText);
                    System.out.println("Morse code: " + morseCode);
                    break;

                case 2:
                    System.out.print("Enter Morse code to convert to text (separate characters with spaces): ");
                    String inputMorse = scanner.nextLine();
                    String translatedText = convertMorseToText(inputMorse);
                    System.out.println("Text: " + translatedText);
                    break;

                case 0:
                    scanner.close();
                    System.exit(0);
                    return;

                default:
                    System.out.println("Wrong choice, try again.");
            }
            System.out.println();
        }
    }

    private static String convertTextToMorse(String input) {
        StringBuilder morseBuilder = new StringBuilder();

        for (char c : input.toCharArray()) {
            if (MorseCodeList.textToMorse.containsKey(c)) {
                morseBuilder.append(MorseCodeList.textToMorse.get(c)).append(" ");
            } else if (c == ' ') {
                morseBuilder.append("/ ");
            }
        }

        return morseBuilder.toString().trim();
    }

    private static String convertMorseToText(String input) {
        StringBuilder resultBuilder = new StringBuilder();

        String[] morseChars = input.split(" ");

        for (String morseChar : morseChars) {
            if (morseChar.equals("/")) {
                resultBuilder.append(" ");
            } else if (MorseCodeList.morseToText.containsKey(morseChar)) {
                resultBuilder.append(MorseCodeList.morseToText.get(morseChar));
            } else {
                resultBuilder.append("?");
            }

            resultBuilder.append("");
        }

        return resultBuilder.toString().trim();
    }
}