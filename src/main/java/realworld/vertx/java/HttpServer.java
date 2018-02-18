package realworld.vertx.java;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import realworld.vertx.java.user.RegisterUser;
import realworld.vertx.java.user.UserService;

/**
 * @author Samer Kanjo
 * @since 0.1.0 1/1/18 1:01 PM
 */
public class HttpServer extends AbstractVerticle {

  private UserService userService;

  @Override
  public void start(Future<Void> startFuture) {
    userService = UserService.createProxy(vertx, "user.queue");

    final Router r = Router.router(vertx);

    r.route("/api/users").handler(BodyHandler.create().setBodyLimit(4096));
    r.post("/api/users").handler(new RegisterUser(userService));

    r.get().handler(event -> {
      event.response().end("<html><head><title>Hello Conduit</title></head><body><h1>Hello Conduit!</h1></body></html>");
    });

    final io.vertx.core.http.HttpServer s = vertx.createHttpServer();

    s.requestHandler(r::accept).listen(port(), result -> {
      if (result.succeeded()) {
        System.out.println("Conduit started on port " + port());
        startFuture.complete();
      } else {
        System.out.println("Failed to start HTTP server");
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
