package realworld.vertx.java.user.identity;

/**
 * @author Samer Kanjo
 * @since 0.2.0 3/31/18 3:42 PM
 */
public interface UserIdentityStore {

  void add(UserIdentity identity);

  boolean contains(String username);

}
