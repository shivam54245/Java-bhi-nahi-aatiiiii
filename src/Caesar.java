import static java.lang.Integer.parseInt;

/**
 * Implements the Caesar cipher by shifting letters by a fixed numeric key.
 * It can transform plain text into cipher text and reverse the process back into plain text.
 */
public class Caesar implements Cipher {

    /** The size of the shift applied to each letter. */
    public int key;
    /** Stores the encrypted output after encryption. */
    public String encryptedMessage;
    /** Stores the plain or decrypted output after decryption. */
    public String rawMessage;

    /**
     * Creates a Caesar cipher object using the provided message and a flag that indicates whether
     * the message is already encrypted.
     *
     * @param key the shift value
     * @param message the message to store
     * @param isEncrypted true when the provided message is encrypted and should be decrypted later
     */
    public Caesar(int key, String message, boolean isEncrypted) {
        this.key = key;

        if (isEncrypted) {
            this.encryptedMessage = message;
            this.rawMessage = null;
        } else {
            this.rawMessage = message;
            this.encryptedMessage = null;
        }
    }

    /**
     * Creates a Caesar cipher object for a new plain message that will be encrypted.
     *
     * @param key the shift value
     * @param rawMessage the plain message to encrypt
     */
    public Caesar(int key, String rawMessage) {
        this.key = key;
        this.encryptedMessage = null;
        this.rawMessage = rawMessage;
    }

    /**
     * Encrypts the stored plain message by shifting each letter by the configured key.
     */
    @Override
    public void encryptMessage() {
        StringBuilder message = new StringBuilder();
        rawMessage = convertToSimpleText(rawMessage);
        for (char letter : rawMessage.toCharArray()) {
            message.append(shiftBy(letter, key));
        }
        encryptedMessage = message.toString();
    }

    /**
     * Decrypts the stored encrypted message by shifting each letter backward by the key.
     */
    @Override
    public void decryptMessage() {
        encryptedMessage = convertToSimpleText(encryptedMessage);
        StringBuilder message = new StringBuilder();
        for (char letter : encryptedMessage.toCharArray()) {
            message.append(shiftBy(letter, -1 * key));
        }
        rawMessage = message.toString();
    }

    /**
     * Runs a simple console-based demo for the Caesar cipher.
     * The user can encrypt, decrypt, or quit from the terminal.
     */
    public static void Interface() {
        label:
        while (true) {
            String mode = IO.readln("Mode: Encrypt [e], Decrypt [d], Quit [q] \n");
            mode = mode.toLowerCase();

            switch (mode) {
                case "e": {
                    int key = parseInt(IO.readln("Key: "));
                    String rawMessage = IO.readln("Message to Encrypt: \n");
                    Caesar caesar = new Caesar(key, rawMessage);
                    caesar.encryptMessage();
                    IO.println(caesar.encryptedMessage);

                    break;
                }
                case "d": {
                    int key = parseInt(IO.readln("Key: "));
                    String encryptedMessage = IO.readln("Message to Decrypt: \n");
                    Caesar caesar = new Caesar(key, encryptedMessage, true);
                    caesar.decryptMessage();
                    IO.println(caesar.rawMessage);
                    break;
                }
                case "q": break label;

                default: IO.println("Invalid Input! Try Again.");
            }
        }
    }
}
