import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;

public class RsaTest {

    @Test
    void TestRsaEncryptionRandom() {
        Rsa rsa = new Rsa();
        BigInteger bigInteger = new BigInteger("5");
        assertEquals(bigInteger, rsa.decrypt(rsa.encrypt(bigInteger)));
    }

    @Test
    void TestRsaEncryptionInteger() {
        Rsa rsa = new Rsa(getExampleKeys());
        BigInteger message = new BigInteger("1773");
        BigInteger encryptedMessage = rsa.encrypt(message);
        assertEquals(message, rsa.decrypt(encryptedMessage));
    }

    @Test
    void TestRsaEncryptionTextFileAndKeysFile() {
        Path pathIn = Path.of("Test Resources/testFile.txt");
        Path pathOutEncryption = Path.of("Test Resources/testFileEncrypted.txt");
        Path pathOutDecryption = Path.of("Test Resources/testFileDecrypted.txt");
        Path pathOutKeys = Path.of("Test Resources/testKeys.txt");

        Rsa rsa = null;
        try {
            rsa = Rsa.loadKeysFromFile(pathOutKeys);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        rsa.encrypt(pathIn, pathOutEncryption);
        rsa.decrypt(pathOutEncryption, pathOutDecryption);

        try {
            String rawMessage = Files.readString(pathIn);
            String decryptedMessage = Files.readString(pathOutDecryption);
            assertEquals(rawMessage, decryptedMessage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @Test
    void TestRsaEncryptionImageFile() {
        Rsa rsa = new Rsa(getExampleKeys());
        Path pathIn = Path.of("Test Resources/testImage.png");
        Path pathOutEncryption = Path.of("Test Resources/testImageEncrypted.png");
        Path pathOutDecryption = Path.of("Test Resources/testImageDecrypted.png");

        rsa.encrypt(pathIn, pathOutEncryption);
        rsa.decrypt(pathOutEncryption, pathOutDecryption);

        try {
            byte[] rawMessage = Files.readAllBytes(pathIn);
            byte[] decryptedMessage = Files.readAllBytes(pathOutDecryption);
            assertArrayEquals(rawMessage, decryptedMessage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    BigInteger[] getExampleKeys() {

        BigInteger[] keyList = new BigInteger[3];
        //modulus
        keyList[0] = new BigInteger("24052027491043601143600897812895533897239476420770590988874009736480430122531667410157835814784041899559260252072127308366864065080815836668377597364492598595753259400407994945876045182908564410594733715261432842211228671984951399859748762379526456210501270993676460153192414940333805195641888001123051550339");
        //public key
        keyList[1] = new BigInteger("3211881409965611791389331717894258474847052656248355433121499839514191556869240642048013683330419710242372715167098480654305362773306153217488624662463439");
        //private key
        keyList[2] = new BigInteger("10800450706267955703896495854691126424866911092946722243265037164556931452167043114609427458150321487772671258566818439847827362717383660229277388471092208660561654623483216578271752146032160992330868438511339214513223343249920612371741848360251859155730557951813206286434192227873538990078392535728352777719");

        return keyList;
    }
}
