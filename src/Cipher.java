/**
 * Shared behavior for all cipher implementations in this project.
 * The interface centralizes the common string-cleaning and letter-shifting logic
 * so each cipher can focus on its own encrypt/decrypt strategy.
 */
public interface Cipher {
    /**
     * The lowercase English alphabet used by the ciphers.
     */
    String alphabet = "abcdefghijklmnopqrstuvwxyz";

    /**
     * Wraps a number into the inclusive range [minimum, maximum].
     * This keeps letter shifts inside the alphabet boundaries.
     *
     * @param number the value to normalize
     * @param minimum the lower bound (inclusive)
     * @param maximum the upper bound (inclusive)
     * @return the normalized value within [minimum, maximum]
     */
    default int normalize(int number, int minimum, int maximum) {
        int range = maximum - minimum;
        return (((number - minimum) % range) + range) % range + minimum;
    }

    /**
     * Removes any characters that are not lowercase letters or spaces.
     * This creates a clean version of the message that the cipher algorithms can process.
     *
     * @param text the original message to sanitize
     * @return a lowercase-only string with spaces preserved
     */
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

    /**
     * Encrypts the current message stored by the implementing class.
     */
    void encryptMessage();

    /**
     * Decrypts the current message stored by the implementing class.
     */
    void decryptMessage();

    /**
     * Shifts a single letter by the provided amount while keeping the result inside the alphabet.
     *
     * @param letter the letter to shift
     * @param shift the number of positions to move the letter
     * @return the shifted letter, or the original whitespace character if the input was whitespace
     */
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

    /**
     * Shifts a single letter by the position of another letter inside the alphabet.
     * This version is used when the shift amount is represented as a letter.
     *
     * @param letter the letter to shift
     * @param shift the letter whose alphabet position is used as the shift amount
     * @return the shifted letter
     */
    default char shiftBy(char letter, char shift) {
        int intShift = alphabet.indexOf(shift);
        return shiftBy(letter, intShift);
    }

    /**
     * Replaces characters in a string based on two alphabet mappings.
     * This is mainly used by substitution-based ciphers.
     *
     * @param string the message to transform
     * @param initial the source alphabet to read from
     * @param replaceBy the target alphabet to write to
     * @return the transformed message
     */
    default String replaceLettersinString(String string, String initial, String replaceBy) {
        string = convertToSimpleText(string);
        StringBuilder message = new StringBuilder();
        for (char letter : string.toCharArray()) {
            if (Character.isWhitespace(letter)) {
                message.append(letter);
            } else {
                int index = initial.indexOf(Character.toLowerCase(letter));
                message.append(replaceBy.charAt(index));
            }
        }
        return message.toString();
    }
}


