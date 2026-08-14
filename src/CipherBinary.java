import java.math.BigInteger;
import java.nio.file.Path;

public interface CipherBinary {

    BigInteger encrypt(BigInteger rawInteger);

    void encrypt(Path pathIn, Path pathOut);

    BigInteger decrypt(BigInteger encryptedInteger);

    void decrypt(Path pathIn, Path pathOut);


}
