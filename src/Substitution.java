import java.beans.SimpleBeanInfo;

public class Substitution implements Cipher {
    String key;
    String encryptedMessage;
    String rawMessage;

//    Constructor
    public Substitution(String key, String rawMessage) {
        this.key = key;
        this.rawMessage = rawMessage;
        this.encryptedMessage = null;
    }

    public Substitution(String key, String encryptedMessage, boolean isEncrypted) {

        this.key = key;

        if (isEncrypted) {
            this.rawMessage = null;
            this.encryptedMessage = encryptedMessage;
        } else {
            this.rawMessage = encryptedMessage;
            this.encryptedMessage = null;
        }
    }

    @Override
    public void encryptMessage() {
        encryptedMessage = replaceLettersinString(rawMessage, alphabet, key);
    }

    @Override
    public void decryptMessage() {
        rawMessage = replaceLettersinString(encryptedMessage, key, alphabet);
    }



    //    standalone interface for testing on substitution
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