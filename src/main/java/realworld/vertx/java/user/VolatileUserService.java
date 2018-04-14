package realworld.vertx.java.user;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Samer Kanjo
 * @since 0.2.0 3/31/18 9:29 AM
 */
public class VolatileUserService implements UserService {

  private static final AtomicLong USER_ID_SEQUENCE = new AtomicLong(100_000);

  private final Map<String, RegisteredUser> users;

  public VolatileUserService() {
    users = new HashMap<>();
  }

  @Override
  public UserService register(UserRegistrationRequest regReq, Handler<AsyncResult<RegisteredUser>> resultHandler) {
    final String userKey = regReq.email();

    if (users.containsKey(userKey)) {
      resultHandler.handle(Future.failedFuture("user already registered"));

    } else {
      final RegisteredUser newUser = RegisteredUser.newBuilder()
        .id(USER_ID_SEQUENCE.incrementAndGet())
        .email(regReq.email())
        .hashedPassword(regReq.hashedPassword())
        .username(regReq.username())
        .build();

      users.put(userKey, newUser);

      resultHandler.handle(Future.succeededFuture(newUser));
    }

    return this;
  }

}
