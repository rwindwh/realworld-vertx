package realworld.vertx.java.user.identity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Samer Kanjo
 * @since 0.2.0 4/13/18 8:23 AM
 */
public class InMemoryUserIdentityStore implements UserIdentityStore {

  private final Map<String, UserIdentity> identities;

  public InMemoryUserIdentityStore() {
    identities = new HashMap<>();
  }

  @Override
  public void add(UserIdentity newIdentity) {
    final String key = newIdentity.username();

    if (contains(key)) {
      throw new IllegalStateException("cannot add identify, it already exists");
    }

    identities.put(key, newIdentity);
  }

  @Override
  public boolean contains(String username) {
    return identities.containsKey(username);
  }

}
