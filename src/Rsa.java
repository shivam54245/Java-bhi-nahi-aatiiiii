import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class Rsa implements CipherBinary {

    public BigInteger modulus;
    public BigInteger publicKey;
    public BigInteger privateKey;
    public BigInteger defaultPublicKey = new BigInteger("65537");


    Rsa() {
        do {
            Random random = new SecureRandom();
            int bitlength = 8 * 127 + 1 + random.nextInt(7);
            BigInteger p = BigInteger.probablePrime(bitlength / 2, random);
            BigInteger q = BigInteger.probablePrime(bitlength / 2, random);

            modulus = p.multiply(q);
            BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
            publicKey = defaultPublicKey;
            try {
                privateKey = publicKey.modInverse(phi);
            } catch (ArithmeticException e) {
                privateKey = BigInteger.ZERO;
            }
        } while (!isKeysValid());

    }

    /** Use only when one key is known. Fails the key validity test.
     * @param modulus modulus for encryption or decryption
     * @param key encryption or decryption key
     * @param encryptMode only works one way, other key is not known
     */
    Rsa(BigInteger modulus, BigInteger key, boolean encryptMode) {
        this.modulus = modulus;

        if(encryptMode) {
            this.publicKey = key;
        } else {
            this.privateKey = key;
        }
    }

    Rsa(BigInteger modulus, BigInteger publicKey, BigInteger privateKey) {
        this.privateKey = privateKey;
        this(modulus, publicKey, true);

    }

    Rsa(BigInteger[] keyList) {
        this(keyList[0], keyList[1], keyList[2]);
    }


    @Override
    public BigInteger encrypt(BigInteger rawInteger) {
        return rawInteger.modPow(publicKey, modulus);
    }

    @Override
    public void encrypt(Path pathIn, Path pathOut) {
        try (InputStream inputStream = Files.newInputStream(pathIn);
             OutputStream outputStream = Files.newOutputStream(pathOut)) {

            byte[] bytes;

            while ((bytes = inputStream.readNBytes(1260)).length > 0) {
                ChunkReader binaryChunks = new ChunkReader(bytes);

                for (BigInteger integer : binaryChunks.finalByteStream) {
                    BigInteger encryptedInteger = encrypt(integer);
                    outputStream.write(appendLeadingZeros(encryptedInteger.toByteArray(), 128));
                }

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BigInteger decrypt(BigInteger encryptedInteger) {
        return encryptedInteger.modPow(privateKey, modulus);
    }

    @Override
    public void decrypt(Path pathIn, Path pathOut) {
        try (InputStream inputStream = Files.newInputStream(pathIn);
             OutputStream outputStream = Files.newOutputStream(pathOut)) {

            byte[] bytes;

            while ((bytes = inputStream.readNBytes(128)).length > 0) {
                BigInteger integer = new BigInteger(1, bytes);
                BigInteger decryptedInteger = decrypt(integer);
                bytes = appendLeadingZeros(decryptedInteger.toByteArray(), 127);
                int dataLength = bytes[0];
                outputStream.write(bytes, 1, dataLength);
                }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    boolean isKeysValid() {
        String testString = "TestString.565ul.843r64$7rnv";
        BigInteger testInteger = new BigInteger(1, testString.getBytes(StandardCharsets.UTF_8));
        if (privateKey == null || privateKey.equals(BigInteger.ZERO)) { return false; }
        else if (publicKey == null || publicKey.equals(BigInteger.ZERO)) { return false; }
        else return testInteger.equals(decrypt(encrypt(testInteger)));
    }


    /**
     * @param rawBytes byte array must be of length <= 128
     * @return byte array of length 128 with leading zeros before rawBytes
     */
    byte[] appendLeadingZeros(byte[] rawBytes, int length) {
        assert rawBytes.length <= length;
        byte[] finalBytes = new byte[length];
        int offset = length - rawBytes.length;
        System.arraycopy(rawBytes, 0, finalBytes, offset, rawBytes.length);
        return finalBytes;
    }

    void writeKeysToFile(Path path) throws IOException {
        String message = "RSA\n" + modulus.toString() + "\n" + publicKey.toString() + "\n" + privateKey.toString();
        Files.writeString(path, message);

    }

    public static Rsa loadKeysFromFile(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path);
        if ("RSA".equals(lines.getFirst())) {
            try {
                BigInteger modulus = new BigInteger(lines.get(1));
                BigInteger publicKey = new BigInteger(lines.get(2));
                BigInteger privateKey = new BigInteger(lines.get(3));

                return new Rsa(modulus, publicKey, privateKey);

            } catch (NumberFormatException e) {
                throw new NumberFormatException("Invalid Keys in File.");
            }

        } else {
            throw new IOException("File does not contain RSA cipher.");
        }
    }
}
