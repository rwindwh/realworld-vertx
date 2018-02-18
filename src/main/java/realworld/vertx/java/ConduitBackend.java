package realworld.vertx.java;

import io.vertx.core.CompositeFuture;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import realworld.vertx.java.user.UserServiceVerticle;

/**
 * @author Samer Kanjo
 * @since 0.1.0 1/1/18 12:53 PM
 */
public class ConduitBackend {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConduitBackend.class);

  public static void main(String[] args) {
    try {
      final VertxOptions vopt = new VertxOptions();
      vopt.setBlockedThreadCheckInterval(20000);

      final Vertx v = Vertx.vertx(vopt);

      final JsonObject conf = new JsonObject().put("listen", 8080);

      final DeploymentOptions dopt = new DeploymentOptions();
      dopt.setConfig(conf);

      final Future<String> userDeployment = Future.future();
      v.deployVerticle(new UserServiceVerticle(), dopt, userDeployment.completer());

      final Future<String> httpDeployment = Future.future();
      v.deployVerticle(new HttpServer(), dopt, httpDeployment.completer());

      CompositeFuture.all(userDeployment, httpDeployment).setHandler(ar -> {
        if (ar.succeeded()) {
          LOGGER.info("Started all verticles.");
        } else {
          LOGGER.info("One or more verticles failed to start.");
        }
      });

      Runtime.getRuntime().addShutdownHook(new Thread(() -> v.close(event -> {
        if (event.succeeded()) {
          LOGGER.info("Shutdown successful");
        } else {
          LOGGER.info("Failed to shutdown");
        }
      })));

    } catch (Exception e) {
      LOGGER.error("error starting app", e);
    }
  }

}
