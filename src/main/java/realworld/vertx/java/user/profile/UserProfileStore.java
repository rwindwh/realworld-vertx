package realworld.vertx.java.user.profile;

/**
 * @author Samer Kanjo
 * @since 0.2.0 3/31/18 3:42 PM
 */
public interface UserProfileStore {

  void add(UserProfile profile);

  boolean contains(String username);

  UserProfile get(String username);

}
