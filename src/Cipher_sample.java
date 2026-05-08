public interface Cipher_sample {
    String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    void encryptMessage();
    void decryptMessage();
    String getOutput();

    default int normalize(int value, int minimum, int maximum) {
        int range = maximum - minimum;
        return (((value - minimum) % range) + range) % range + minimum;
    }

    default String normalizeText(String text) {
        if (text == null) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        for (char ch : text.toCharArray()) {
            if (Character.isWhitespace(ch)) {
                result.append(ch);
                continue;
            }
            char lower = Character.toLowerCase(ch);
            if (ALPHABET.indexOf(lower) >= 0) {
                result.append(lower);
            }
        }
        return result.toString();
    }

    default char shiftLetter(char letter, int shift) {
        if (Character.isWhitespace(letter)) {
            return letter;
        }
        int index = ALPHABET.indexOf(letter);
        if (index < 0) {
            return letter;
        }
        int newIndex = normalize(index + shift, 0, ALPHABET.length());
        return ALPHABET.charAt(newIndex);
    }

    default String replaceLetters(String text, String source, String target) {
        String normalized = normalizeText(text);
        StringBuilder result = new StringBuilder(normalized.length());

        for (char ch : normalized.toCharArray()) {
            if (Character.isWhitespace(ch)) {
                result.append(ch);
                continue;
            }
            int sourceIndex = source.indexOf(ch);
            if (sourceIndex < 0) {
                continue;
            }
            result.append(target.charAt(sourceIndex));
        }
        return result.toString();
    }
}
