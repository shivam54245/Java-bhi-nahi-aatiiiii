public interface Cipher {
    String alphabet = "abcdefghijklmnopqrstuvwxyz";


    /**
     * Normalizes a number into the range [minimum, maximum], inclusive.
     *
     * @param number  the value to normalize
     * @param minimum the lower bound (inclusive)
     * @param maximum the upper bound (inclusive)
     * @return the normalized value within [minimum, maximum]
     */
    default int normalize(int number, int minimum, int maximum) {
        int range = maximum - minimum;
        return (((number - minimum) % range) + range) % range + minimum;
    }

    default String convertToSimpleText(String text) {
        StringBuilder simpletext = new StringBuilder();
        for (char letter : text.toCharArray()) {

            if (alphabet.indexOf(Character.toLowerCase(letter)) != -1) {
                simpletext.append(Character.toLowerCase(letter));

            } else if (Character.isWhitespace(letter)) {
                simpletext.append(letter);

            }
        }
        return simpletext.toString();
    }


    void encryptMessage();
    void decryptMessage();

    default char shiftBy(char letter, int shift) {
        if (Character.isWhitespace(letter)) {
            return letter;
        } else {
            int index = alphabet.indexOf(letter);
            int newIndex = index + shift;
            newIndex = normalize(newIndex, 0, 26);
            return alphabet.charAt(newIndex);
        }
    }

    default char shiftBy(char letter, char shift) {
        int intShift = alphabet.indexOf(shift);
        return shiftBy(letter, intShift);
    }

    default String replaceLettersinString(String string, String initial, String replaceBy) {
        string = convertToSimpleText(string);
        StringBuilder message = new StringBuilder();
        for (char letter : string.toCharArray()) {
            if(Character.isWhitespace(letter)) {
                message.append(letter);
            } else {
                int index = initial.indexOf(Character.toLowerCase(letter));
                message.append(replaceBy.charAt(index));
            }
        }
        return message.toString();
    }
}


