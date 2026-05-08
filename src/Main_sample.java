public class Main_sample {
    public static void main(String[] args) {
        CipherDemo_sample.run("Caesar Cipher", new CaesarFactory());
        CipherDemo_sample.run("Substitution Cipher", new SubstitutionFactory());
    }

    private static class CaesarFactory implements CipherDemo_sample.CipherFactory_sample {
        @Override
        public Cipher_sample createForEncryption(String key, String message) {
            int numericKey = Integer.parseInt(key.trim());
            return new Caesar_sample(numericKey, message);
        }

        @Override
        public Cipher_sample createForDecryption(String key, String message) {
            int numericKey = Integer.parseInt(key.trim());
            return new Caesar_sample(numericKey, message);
        }
    }

    private static class SubstitutionFactory implements CipherDemo_sample.CipherFactory_sample {
        @Override
        public Cipher_sample createForEncryption(String key, String message) {
            return new Substitution_sample(key, message);
        }

        @Override
        public Cipher_sample createForDecryption(String key, String message) {
            return new Substitution_sample(key, message);
        }
    }
}
