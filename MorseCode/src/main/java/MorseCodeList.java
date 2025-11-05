import java.util.HashMap;

/**
 *
 *@author Sma1lo
 */

public class MorseCodeList {
    static final HashMap<Character, String> textToMorse = new HashMap<>();
    static final HashMap<String, Character> morseToText = new HashMap<>();

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
}

