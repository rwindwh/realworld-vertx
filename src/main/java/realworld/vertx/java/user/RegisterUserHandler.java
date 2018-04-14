package realworld.vertx.java.user;

import io.vertx.core.Handler;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Samer Kanjo
 * @since 0.2.0 1/1/18 11:38 PM
 */
class RegisterUserHandler implements Handler<RoutingContext> {

  private static final Logger LOGGER = LoggerFactory.getLogger(RegisterUserHandler.class);

  private final UserService userService;

  RegisterUserHandler(UserService userService) {
    this.userService = userService;
  }

  @Override
  public void handle(RoutingContext event) {
    try {
      if (event.getBody() == null || event.getBody().length() == 0) {
        event.response().setStatusCode(422).end("{\"errors\":{\"body\":[\"can't be empty\"]}}");
        return;
      }

      final UserRegistrationRequest regReq = UserRegistrationRequest.parseFrom(event.getBodyAsJson());

      // validate request

      userService.register(regReq, registration -> {
        if (registration.succeeded()) {

          final RegisteredUser regUser = registration.result();
          final AuthenticatedUser authUser = AuthenticatedUser.newBuilder()
            .user(regUser)
            .token(WebTokens.create(regUser.email()))
            .build();

          final JsonObject json = new JsonObject();
          authUser.writeTo(json);

          event.response().putHeader("Content-Type", "application/json; charset=utf-8");
          event.response().end(json.encode());

        } else {
          event.response().setStatusCode(500).end("failed to register user");
        }
      });

    } catch (DecodeException e) {
      event.response().setStatusCode(422).end("{\"errors\":{\"body\":[\"invalid json\"]}}");
      LOGGER.warn("Failed to decode JSON", e);
    }
  }

}
