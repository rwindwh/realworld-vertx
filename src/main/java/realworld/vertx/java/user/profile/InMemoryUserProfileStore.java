package realworld.vertx.java.user.profile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Samer Kanjo
 * @since 0.2.0 4/13/18 8:24 AM
 */
public class InMemoryUserProfileStore implements UserProfileStore {

  private final Map<String, UserProfile> profiles;

  public InMemoryUserProfileStore() {
    profiles = new HashMap<>();
  }

  @Override
  public void add(UserProfile newProfile) {
    final String key = newProfile.username();

    if (contains(key)) {
      throw new IllegalStateException("cannot add profile, it already exists");
    }

    profiles.put(key, newProfile);
  }

  @Override
  public boolean contains(String username) {
    return profiles.containsKey(username);
  }

  @Override
  public UserProfile get(String username) {
    if (!contains(username)) {
      throw new IllegalArgumentException("profile does not exist");
    }

    return profiles.get(username);
  }

}
