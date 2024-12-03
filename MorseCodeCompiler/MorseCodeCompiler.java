import java.util.HashMap;
import java.util.Scanner;

public class MorseCodeCompiler {

    private static final HashMap<Character, String> textToMorse = new HashMap<>();
    private static final HashMap<String, Character> morseToText = new HashMap<>();

    static {
        textToMorse.put('A', ".-");
        textToMorse.put('B', "-...");
        textToMorse.put('C', "-.-.");
        textToMorse.put('D', "-..");
        textToMorse.put('E', ".");
        textToMorse.put('F', "..-.");
        textToMorse.put('G', "--.");
        textToMorse.put('H', "....");
        textToMorse.put('I', "..");
        textToMorse.put('J', ".---");
        textToMorse.put('K', "-.-");
        textToMorse.put('L', ".-..");
        textToMorse.put('M', "--");
        textToMorse.put('N', "-.");
        textToMorse.put('O', "---");
        textToMorse.put('P', ".--.");
        textToMorse.put('Q', "--.-");
        textToMorse.put('R', ".-.");
        textToMorse.put('S', "...");
        textToMorse.put('T', "-");
        textToMorse.put('U', "..-");
        textToMorse.put('V', "...-");
        textToMorse.put('W', ".--");
        textToMorse.put('X', "-..-");
        textToMorse.put('Y', "-.--");
        textToMorse.put('Z', "--..");

        for (HashMap.Entry<Character, String> entry : textToMorse.entrySet()) {
            morseToText.put(entry.getValue(), entry.getKey());
        }
   
        for (int i = 0; i <= 9; i++) {
            morseToText.put(String.valueOf(i), (char) ('0' + i));
            switch (i) {
                case 0: 
                    textToMorse.put((char) ('0' + i), "-----"); break;
                case 1: 
                    textToMorse.put((char) ('0' + i), ".----"); break;
                case 2: 
                    textToMorse.put((char) ('0' + i), "..---"); break;
                case 3: 
                    textToMorse.put((char) ('0' + i), "...--"); break;
                case 4: 
                    textToMorse.put((char) ('0' + i), "....-"); break;
                case 5: 
                    textToMorse.put((char) ('0' + i), "....."); break;
                case 6: 
                    textToMorse.put((char) ('0' + i), "-...."); break;
                case 7: 
                    textToMorse.put((char) ('0' + i), "--..."); break;
                case 8: 
                    textToMorse.put((char) ('0' + i), "---.."); break;
                case 9: 
                    textToMorse.put((char) ('0' + i), "----."); break;
            }
            morseToText.put(textToMorse.get((char) ('0' + i)), (char) ('0' + i));
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("1. Convert text to Morse code");
            System.out.println("2. Translate Morse code into text");
            System.out.println("3. Exit\n");
            System.out.print("Enter number: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 
            
            System.out.print("\033[H\033[J");
            
            switch (choice) {
                case 1:
                    System.out.print("Enter text to be translated into Morse code: ");
                    String inputText = scanner.nextLine().toUpperCase();
                    
                    System.out.print("\033[H\033[J");
                    
                    String morseCode = convertTextToMorse(inputText);
                    System.out.println("Morse code: " + morseCode);
                    break;

                case 2:
                    System.out.print("Enter Morse code to convert to text (separate characters with spaces): ");
                    String inputMorse = scanner.nextLine();
                    System.out.print("\033[H\033[J");
                    String translatedText = convertMorseToText(inputMorse);
                    System.out.println("Text: " + translatedText);
                    break;

                case 3:
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
            if (textToMorse.containsKey(c)) {
                morseBuilder.append(textToMorse.get(c)).append(" ");
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
            } else if (morseToText.containsKey(morseChar)) {
                resultBuilder.append(morseToText.get(morseChar));
            } else {
                resultBuilder.append("?");
            }
            
            resultBuilder.append(""); 
       }

       return resultBuilder.toString().trim();
   }
}
