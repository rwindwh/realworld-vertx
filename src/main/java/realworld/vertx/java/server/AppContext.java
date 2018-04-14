package realworld.vertx.java.server;

import realworld.vertx.java.user.UserService;
import realworld.vertx.java.user.VolatileUserService;

/**
 * @author Samer Kanjo
 * @since 0.2.0 3/31/18 9:27 AM
 */
public class AppContext {

  private UserService userService;

  public UserService userService() {
    if (userService == null) {
      userService = new VolatileUserService();
    }
    return userService;
  }
  
}
