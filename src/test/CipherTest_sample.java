import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class CipherTest_sample {

    @ParameterizedTest
    @CsvSource({
            "0, a, a",
            "1, a, b",
            "3, hello world, khoor zruog",
            "25, z, y",
            "26, hello, hello",
            "-1, b, a"
    })
    void caesar_encryptProducesExpectedOutput(int key, String raw, String expected) {
        Caesar_sample cipher = new Caesar_sample(key, raw);
        cipher.encryptMessage();

        assertEquals(expected, cipher.getOutput());
    }

    @ParameterizedTest
    @CsvSource({
            "0, a, a",
            "1, b, a",
            "3, khoor zruog, hello world",
            "25, y, z",
            "26, hello, hello",
            "-1, a, b"
    })
    void caesar_decryptProducesExpectedOutput(int key, String encrypted, String expected) {
        Caesar_sample cipher = new Caesar_sample(key, encrypted);
        cipher.decryptMessage();

        assertEquals(expected, cipher.getOutput());
    }

    @Test
    void substitution_encryptAndDecryptWithValidKey() {
        String key = "zyxwvutsrqponmlkjihgfedcba";
        String raw = "hello world";

        Substitution_sample encryptCipher = new Substitution_sample(key, raw);
        encryptCipher.encryptMessage();
        String encrypted = encryptCipher.getOutput();

        Substitution_sample decryptCipher = new Substitution_sample(key, encrypted);
        decryptCipher.decryptMessage();
        assertEquals(raw, decryptCipher.getOutput());
    }

    @Test
    void substitution_invalidKeyThrows() {
        String invalidKey = "abc";
        Substitution_sample cipher = new Substitution_sample(invalidKey, "hello");

        assertThrows(IllegalArgumentException.class, cipher::encryptMessage);
    }
}
