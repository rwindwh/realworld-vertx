package realworld.vertx.java.server;

import realworld.vertx.java.user.*;
import realworld.vertx.java.user.identity.InMemoryUserIdentityStore;
import realworld.vertx.java.user.identity.UserIdentityStore;
import realworld.vertx.java.user.profile.InMemoryUserProfileStore;
import realworld.vertx.java.user.profile.UserProfileStore;

/**
 * @author Samer Kanjo
 * @since 0.2.0 3/31/18 9:27 AM
 */
public class AppContext {

  private UserService userService;
  private UserIdentityStore identityStore;
  private UserProfileStore profileStore;

  public UserService userService() {
    if (userService == null) {
      userService = new LocalUserService(identityStore(), profileStore());
    }
    return userService;
  }

  UserIdentityStore identityStore() {
    if (identityStore == null) {
      identityStore = new InMemoryUserIdentityStore();
    }
    return identityStore;
  }

  UserProfileStore profileStore() {
    if (profileStore == null) {
      profileStore = new InMemoryUserProfileStore();
    }
    return profileStore;
  }

}
