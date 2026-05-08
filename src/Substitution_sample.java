public class Substitution_sample implements Cipher_sample {
    private final String key;
    private final String message;
    private String output;

    public Substitution_sample(String key, String message) {
        this.key = key == null ? "" : key.toLowerCase();
        this.message = message;
    }

    @Override
    public void encryptMessage() {
        if (!hasValidKey()) {
            throw new IllegalArgumentException("Substitution key must contain 26 unique letters.");
        }
        output = replaceLetters(message, ALPHABET, key);
    }

    @Override
    public void decryptMessage() {
        if (!hasValidKey()) {
            throw new IllegalArgumentException("Substitution key must contain 26 unique letters.");
        }
        output = replaceLetters(message, key, ALPHABET);
    }

    @Override
    public String getOutput() {
        return output;
    }

    public boolean hasValidKey() {
        if (key.length() != ALPHABET.length()) {
            return false;
        }
        for (char ch : key.toCharArray()) {
            if (ALPHABET.indexOf(ch) < 0 || key.indexOf(ch) != key.lastIndexOf(ch)) {
                return false;
            }
        }
        return true;
    }
}
