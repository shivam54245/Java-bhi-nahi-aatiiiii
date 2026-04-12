import static java.lang.Integer.parseInt;

public class Caesar implements Cipher {

    public int key;
    public String encryptedMessage;
    public String rawMessage;


//    constructor
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

    public Caesar(int key, String rawMessage) {
        this.key = key;
        this.encryptedMessage = null;
        this.rawMessage = rawMessage;
    }


//    encryption and decryption
    @Override
    public void encryptMessage() {
        StringBuilder message = new StringBuilder();
        rawMessage = convertToSimpleText(rawMessage);
        for (char letter : rawMessage.toCharArray()) {
            message.append(shiftBy(letter, key));
        }
        encryptedMessage = message.toString();
    }

    @Override
    public void decryptMessage() {
        encryptedMessage = convertToSimpleText(encryptedMessage);
        StringBuilder message = new StringBuilder();
        for (char letter : encryptedMessage.toCharArray()) {
            message.append(shiftBy(letter, -1 * key));
        }
        rawMessage = message.toString();
    }


//    standalone interface for testing on caesar
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
