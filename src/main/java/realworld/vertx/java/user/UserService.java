package realworld.vertx.java.user;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import realworld.vertx.java.user.identity.UserIdentity;
import realworld.vertx.java.user.profile.UserProfile;

/**
 * @author Samer Kanjo
 * @since 0.2.0 2/12/18 10:16 PM
 */
public interface UserService {

  UserService register(UserIdentity newIdentity, Handler<AsyncResult<Void>> resultHandler);

  UserService get(String username, Handler<AsyncResult<UserProfile>> resultHandler);

}
