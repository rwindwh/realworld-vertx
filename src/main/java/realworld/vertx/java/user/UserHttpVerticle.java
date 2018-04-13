package realworld.vertx.java.user;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * @author Samer Kanjo
 * @since 0.2.0 2/18/18 4:54 PM
 */
public class UserHttpVerticle extends AbstractVerticle {

  private final UserService userService;

  public UserHttpVerticle(UserService userService) {
    this.userService = userService;
  }

  @Override
  public void start(Future<Void> startFuture) {
    final Router r = Router.router(vertx);

    r.route("/api/users").handler(BodyHandler.create().setBodyLimit(512));
    r.post("/api/users").handler(new RegisterUserHandler(userService));

    r.get().handler(event -> {
      event.response().end("<html><head><title>Hello Conduit</title></head><body><h1>Hello Conduit!</h1></body></html>");
    });

    final io.vertx.core.http.HttpServer s = vertx.createHttpServer();

    s.requestHandler(r::accept).listen(port(), result -> {
      if (result.succeeded()) {
        startFuture.complete();
      } else {
        startFuture.fail(result.cause());
      }
    });

  }

  @Override
  public void stop(Future<Void> stopFuture) throws Exception {
    super.stop(stopFuture);
  }

  private int port() {
    return config().getInteger("listen", 8080);
  }
}
