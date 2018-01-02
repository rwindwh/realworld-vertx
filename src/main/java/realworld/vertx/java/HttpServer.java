package realworld.vertx.java;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.web.Router;

/**
 * @author Samer Kanjo
 * @since 0.1.0 1/1/18 1:01 PM
 */
public class HttpServer extends AbstractVerticle {

  @Override
  public void start(Future<Void> startFuture) {
    final Router r = Router.router(vertx);
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
