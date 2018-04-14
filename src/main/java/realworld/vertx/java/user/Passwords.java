package realworld.vertx.java.user;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

/**
 * @author Samer Kanjo
 * @since 0.2.0 3/31/18 5:09 PM
 */
class Passwords {

  private static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";

  private static final int SALT_BYTE_SIZE = 24;
  private static final int HASH_BYTE_SIZE = 18;
  private static final int PBKDF2_ITERATIONS = 64000;

  private static final int HASH_SECTIONS = 2;
  private static final int SALT_INDEX = 0;
  private static final int PBKDF2_INDEX = 1;

  static String hash(String password) {
    final SecureRandom random = new SecureRandom();
    final byte[] salt = new byte[SALT_BYTE_SIZE];
    random.nextBytes(salt);

    final byte[] dk = pkbdf2(password, salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);

    final Base64.Encoder enc = Base64.getUrlEncoder().withoutPadding();
    final String hash = enc.encodeToString(salt) + ":" + enc.encodeToString(dk);

    return hash;
  }

  static boolean verify(String password, String storedHash) {
    final String[] parts = storedHash.split(":");

    if (parts.length != HASH_SECTIONS) {
      throw new IllegalArgumentException("Expected hash is missing parts");
    }

    final Base64.Decoder dec = Base64.getUrlDecoder();
    final byte[] salt = dec.decode(parts[SALT_INDEX]);
    final byte[] expectedHash = dec.decode(parts[PBKDF2_INDEX]);

    final byte[] actualHash = pkbdf2(password, salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);

    return slowEquals(expectedHash, actualHash);
  }

  private static byte[] pkbdf2(String password, byte[] salt, int iterations, int hashByteSize) {
    try {
      final KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, hashByteSize * 8);
      final SecretKeyFactory f = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
      return f.generateSecret(spec).getEncoded();

    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException(PBKDF2_ALGORITHM + " algorithm is not supported", e);

    } catch (InvalidKeySpecException e) {
      throw new IllegalStateException("invalid key spec", e);
    }
  }

  private static boolean slowEquals(byte[] expected, byte[] actual) {
    int diff = expected.length ^ actual.length;
    for (int i = 0; i < expected.length && i < actual.length; i++)
      diff |= expected[i] ^ actual[i];
    return diff == 0;
  }

}
