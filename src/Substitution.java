
/**
 * Implements the substitution cipher by mapping the alphabet to a custom keyword-based key.
 * It can encrypt plain text into a substituted version and reverse it back again.
 */
public class Substitution implements Cipher {
    /** The substitution key used to map the original alphabet to the cipher alphabet. */
    String key;
    /** Stores the output of encryption. */
    String encryptedMessage;
    /** Stores the output of decryption or the original plain message before encryption. */
    String rawMessage;

    /**
     * Creates a substitution cipher for a new plain message that will be encrypted.
     *
     * @param key the substitution key
     * @param rawMessage the plain message to encrypt
     */
    public Substitution(String key, String rawMessage) {
        this.key = key.toLowerCase();
        this.rawMessage = rawMessage;
        this.encryptedMessage = null;
    }

    /**
     * Creates a substitution cipher using a message and a flag that indicates whether it is encrypted.
     *
     * @param key the substitution key
     * @param encryptedMessage the message to store
     * @param isEncrypted true if the data should be treated as cipher text
     */
    public Substitution(String key, String encryptedMessage, boolean isEncrypted) {
        this.key = key.toLowerCase();

        if (isEncrypted) {
            this.rawMessage = null;
            this.encryptedMessage = encryptedMessage;
        } else {
            this.rawMessage = encryptedMessage;
            this.encryptedMessage = null;
        }
    }

    /**
     * Encrypts the stored plain message using the substitution key.
     */
    @Override
    public void encryptMessage() {
        encryptedMessage = replaceLettersinString(rawMessage, alphabet, key);
    }

    /**
     * Decrypts the stored encrypted message back into plain text.
     */
    @Override
    public void decryptMessage() {
        rawMessage = replaceLettersinString(encryptedMessage, key, alphabet);
    }

    /**
     * Runs a simple console-based demo for the substitution cipher.
     * The user can encrypt, decrypt, or quit from the terminal.
     */
    public static void Interface() {
        label:
        while (true) {
            String mode = IO.readln("Mode: Encrypt [e], Decrypt [d], Quit [q] \n");
            mode = mode.toLowerCase();

            switch (mode) {
                case "e": {
                    String key = IO.readln("Key: ");
                    String rawMessage = IO.readln("Message to Encrypt: \n");
                    Substitution substitution = new Substitution(key, rawMessage);
                    substitution.encryptMessage();
                    IO.println(substitution.encryptedMessage);

                    break;
                }
                case "d": {
                    String key = IO.readln("Key: ");
                    String encryptedMessage = IO.readln("Message to Decrypt: \n");
                    Substitution substitution = new Substitution(key, encryptedMessage, true);
                    substitution.decryptMessage();
                    IO.println(substitution.rawMessage);
                    break;
                }
                case "q": break label;

                default: IO.println("Invalid Input! Try Again.");
            }
        }
    }
}
