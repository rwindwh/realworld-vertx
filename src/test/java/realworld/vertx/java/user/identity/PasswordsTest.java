package realworld.vertx.java.user.identity;

import org.junit.Test;
import realworld.vertx.java.user.identity.Passwords;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author Samer Kanjo
 * @since 0.2.0 3/31/18 10:04 PM
 */
public class PasswordsTest {

  @Test
  public void hashAndVerify() {
    // Setup
    final String password = "password123";

    // Exercise
    final String saltAndHash = Passwords.hash(password);
    final boolean verified = Passwords.verify(password, saltAndHash);

    // Verify
    assertThat(verified).isTrue();
  }

}
