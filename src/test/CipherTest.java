import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class CipherTest {

//for caesar
    @SuppressWarnings("SpellCheckingInspection")
    @ParameterizedTest
    @CsvSource({
            // key, rawMessage, expectedOutput
            "0, a, a",
            "1, a, b",
            "2, a, c",
            "3, m, p",
            "10, y, i",
            "25, z, y",
            "26, hello, hello",          // full rotation
            "-1, b, a",
            "-25, a, b",
            "-30, z, v",
            "3, HELLO, khoor",           // uppercase → lowercase
            "3, hello world, khoor zruog",
            "5, At3453, fy",             // digits dropped, uppercase → lowercase
            "7, vst4YF;]:}{:, czafm"       // numbers/specials dropped, uppercase → lowercase
    })
    void testCaesarEncryption(int key, String rawMessage, String expectedOutput) {
        Caesar caesar = new Caesar(key, rawMessage);
        caesar.encryptMessage();
        IO.println(expectedOutput + "\n" + caesar.encryptedMessage + "\n\n\n");
        assertEquals(expectedOutput, caesar.encryptedMessage);
    }


    @SuppressWarnings("SpellCheckingInspection")
    @ParameterizedTest
    @CsvSource({
            // key, encryptedMessage, expectedDecryptedOutput
            "0, a, a",
            "1, b, a",
            "2, c, a",
            "3, p, m",
            "10, i, y",
            "25, y, z",
            "26, hello, hello",          // full rotation
            "-1, a, b",
            "-25, b, a",
            "-30, v, z",
            "3, khoor, hello",           // lowercase result
            "3, khoor zruog, hello world",
            "5, fy, at",                 // digits dropped, uppercase → lowercase
            "7, czafm, vstyf"                // numbers/specials dropped, uppercase → lowercase
    })
    void testCaesarDecryption(int key, String encryptedMessage, String expectedRawOutput) {
        Caesar caesar = new Caesar(key, encryptedMessage, true);
        caesar.decryptMessage();
        IO.println(expectedRawOutput + "\n" + caesar.rawMessage + "\n\n\n");
        assertEquals(expectedRawOutput, caesar.rawMessage);

    }
}

