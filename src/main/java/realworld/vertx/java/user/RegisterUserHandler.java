package realworld.vertx.java.user;

import io.vertx.core.Handler;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import realworld.vertx.java.user.identity.UserIdentity;
import realworld.vertx.java.user.profile.UserProfile;

/**
 * @author Samer Kanjo
 * @since 0.2.0 1/1/18 11:38 PM
 */
public class RegisterUserHandler implements Handler<RoutingContext> {

  private static final Logger LOGGER = LoggerFactory.getLogger(RegisterUserHandler.class);

  private final UserService userService;

  public RegisterUserHandler(UserService userService) {
    this.userService = userService;
  }

  @Override
  public void handle(RoutingContext event) {
    try {
      if (event.getBody() == null || event.getBody().length() == 0) {
        event.response().setStatusCode(422).end("{\"errors\":{\"body\":[\"can't be empty\"]}}");
        return;
      }

      final UserIdentity newIdentity = UserIdentity.parseFrom(event.getBodyAsJson());

      LOGGER.debug(newIdentity.toString());

      // validate request

      userService.register(newIdentity, reply -> {
        if (reply.succeeded()) {

          userService.get(newIdentity.username(), get -> {
            if (get.succeeded()) {
              final UserProfile user = get.result();
              final JsonObject json = new JsonObject();
              user.writeTo(json);

              event.response().putHeader("Content-Type", "application/json; charset=utf-8");
              event.response().end(json.encode());

            } else {
              event.response().setStatusCode(500).end("failed to get user after registration");

            }
          });

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
