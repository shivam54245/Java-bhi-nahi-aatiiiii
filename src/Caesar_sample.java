public class Caesar_sample implements Cipher_sample {
    private final int key;
    private final String message;
    private String output;

    public Caesar_sample(int key, String message) {
        this.key = normalize(key, 0, ALPHABET.length());
        this.message = message;
    }

    @Override
    public void encryptMessage() {
        output = transform(message, key);
    }

    @Override
    public void decryptMessage() {
        output = transform(message, -key);
    }

    @Override
    public String getOutput() {
        return output;
    }

    private String transform(String text, int shift) {
        String normalized = normalizeText(text);
        StringBuilder result = new StringBuilder(normalized.length());
        for (char ch : normalized.toCharArray()) {
            result.append(shiftLetter(ch, shift));
        }
        return result.toString();
    }
}
