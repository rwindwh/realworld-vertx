package realworld.vertx.java.user;

import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Samer Kanjo
 * @since 0.2.0 1/1/18 11:38 PM
 */
public class RegisterUser implements Handler<RoutingContext> {

  private static final Logger LOGGER = LoggerFactory.getLogger(RegisterUser.class);

  private final UserService userService;

  public RegisterUser(UserService userService) {
    this.userService = userService;
  }

  @Override
  public void handle(RoutingContext event) {
    final Buffer b = event.getBody();
    if (b == null || b.length() == 0) {
      event.response().setStatusCode(422).end("{\"errors\":{\"body\":[\"can't be empty\"]}}");
      return;
    }

    try {
      final JsonObject body = new JsonObject(b.toString());
      final RegistrationRequest regReq = RegistrationRequest.builder().fromJson(body).build();
      LOGGER.debug(regReq.toString());

      userService.register(regReq.username(), regReq.password(), regReq.email(), reply -> {
        if (reply.succeeded()) {
          event.response().setStatusCode(200).end("registered user");
        } else {
          event.response().setStatusCode(500).end("failed to registered user");
        }
      });

      userService.all(reply -> {
        if (reply.succeeded()) {
          LOGGER.debug(reply.result().encode());
        } else {
          LOGGER.debug("Failed to get all users", reply.cause());
        }
      });

    } catch (DecodeException e) {
      event.response().setStatusCode(422).end("{\"errors\":{\"body\":[\"invalid json\"]}}");
    }
  }

}
