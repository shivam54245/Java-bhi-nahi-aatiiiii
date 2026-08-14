void main(String[] args) {

    if (args.length == 0) {
        args = IO.readln().trim().split("\\s+");
    }
    if (args.length < 2) {
        printUsage();
        return;
    }

    String mode = args[0].toLowerCase(Locale.ROOT);
    if (mode.equals("encrypt")) {
        try {
            encryptMode(args);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    } else if (mode.equals("decrypt")) {
        try {
            decryptMode(args);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    } else {
        printUsage();
    }
}

private static void encryptMode(String[] args) throws IOException {
    if (args.length < 5) {
        IO.println("Format: <mode> <cipher> <input_file> <output_file> <keys_file>");
    } else {

        String cipher = args[1].toLowerCase(Locale.ROOT);
        Path inputFile = Path.of(args[2]);
        Path outputFile = Path.of(args[3]);
        Path keysFile = Path.of(args[4]);

        switch (cipher) {
            case "rsa":
                try {
                    Rsa rsa = Rsa.loadKeysFromFile(keysFile);
                    rsa.encrypt(inputFile, outputFile);
                } catch (NoSuchFileException e) {
                    Rsa rsa = new Rsa();
                    rsa.encrypt(inputFile, outputFile);
                    rsa.writeKeysToFile(keysFile);
                }
                break;

            case "caesar", "substitution":
                IO.println("Not Implemented.");
                break;

            default:
                throw new IllegalArgumentException("Unknown Cipher Algorithm: " + cipher);

        }

    }

}

private static void decryptMode(String[] args) throws IOException {
    if (args.length < 5) {
        IO.println("Format: <mode> <cipher> <input_file> <output_file> <keys_file>");
    } else {

        String cipher = args[1].toLowerCase(Locale.ROOT);
        Path inputFile = Path.of(args[2]);
        Path outputFile = Path.of(args[3]);
        Path keysFile = Path.of(args[4]);

        switch (cipher) {
            case "rsa":
                try {
                    Rsa rsa = Rsa.loadKeysFromFile(keysFile);
                    rsa.decrypt(inputFile, outputFile);
                } catch (NoSuchFileException e) {
                    IO.println("Keys File Not Found.");
                }
                break;

            case "caesar", "substitution":
                IO.println("Not Implemented.");
                break;

            default:
            throw new IllegalArgumentException("Unknown Cipher Algorithm: " + cipher);
        }
    }
}

private static void printUsage() {
    IO.println("""
            mode: Encrypt, Decrypt
            ciphers: Caesar, Substitution, RSA
            input_file: File from where data is to be read. Can have any format.
            output_file: File to which data has to be written. Can have any format.
            keys_file; File from where keys are to be read. Must follow strict formatting rules.""");
}
