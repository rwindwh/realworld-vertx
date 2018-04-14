package realworld.vertx.java.user;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * @author Samer Kanjo
 * @since 0.2.0 2/12/18 10:16 PM
 */
public interface UserService {

  UserService register(UserRegistrationRequest regReq, Handler<AsyncResult<RegisteredUser>> resultHandler);

}
