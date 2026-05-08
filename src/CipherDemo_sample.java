public class CipherDemo_sample {
    public interface CipherFactory_sample {
        Cipher_sample createForEncryption(String key, String message);
        Cipher_sample createForDecryption(String key, String message);
    }

    public static void run(String title, CipherFactory_sample factory) {
        ConsoleIO_sample.println("--- " + title + " ---");
        while (true) {
            String mode = ConsoleIO_sample.read("Mode: Encrypt [e], Decrypt [d], Quit [q]");
            switch (mode.toLowerCase()) {
                case "e" -> runEncrypt(factory);
                case "d" -> runDecrypt(factory);
                case "q" -> {
                    ConsoleIO_sample.println("Exiting " + title + " demo.");
                    return;
                }
                default -> ConsoleIO_sample.println("Invalid input. Please choose e, d, or q.");
            }
        }
    }

    private static void runEncrypt(CipherFactory_sample factory) {
        String key = ConsoleIO_sample.read("Key:");
        String message = ConsoleIO_sample.read("Message to encrypt:");
        try {
            Cipher_sample cipher = factory.createForEncryption(key, message);
            cipher.encryptMessage();
            ConsoleIO_sample.println("Result: " + cipher.getOutput());
        } catch (RuntimeException ex) {
            ConsoleIO_sample.println("Error: " + ex.getMessage());
        }
    }

    private static void runDecrypt(CipherFactory_sample factory) {
        String key = ConsoleIO_sample.read("Key:");
        String message = ConsoleIO_sample.read("Message to decrypt:");
        try {
            Cipher_sample cipher = factory.createForDecryption(key, message);
            cipher.decryptMessage();
            ConsoleIO_sample.println("Result: " + cipher.getOutput());
        } catch (RuntimeException ex) {
            ConsoleIO_sample.println("Error: " + ex.getMessage());
        }
    }
}
