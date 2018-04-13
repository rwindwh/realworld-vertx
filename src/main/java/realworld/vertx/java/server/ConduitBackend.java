package realworld.vertx.java.server;

import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import realworld.vertx.java.user.UserHttpVerticle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Samer Kanjo
 * @since 0.1.0 1/1/18 12:53 PM
 */
public class ConduitBackend {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConduitBackend.class);

  public static void main(String[] args) {
    try {
      final VertxOptions vopt = new VertxOptions();
      vopt.setBlockedThreadCheckInterval(200000);
      final int maxInstances = vopt.getEventLoopPoolSize();

      final AppContext context = new AppContext();
      final List<Verticle> verticles = new ArrayList<>();
      for (int i = 0; i < maxInstances; i++) {
        verticles.add(new UserHttpVerticle(context.userService()));
      }

      final JsonObject conf = new JsonObject().put("listen", 8080);
      final Vertx vtx = Vertx.vertx(vopt);
      final AtomicInteger deployCount = new AtomicInteger();
      for (Verticle v : verticles) {
        vtx.deployVerticle(v, deploy -> {
          if (deploy.succeeded()) {
            if (deployCount.incrementAndGet() == maxInstances) {
              System.out.println("Conduit started on port " + conf.getInteger("listen"));
            }
          } else {
            System.out.println("Failed to deploy instance, shutting down...");
            vtx.close();
          }
        });
      }

      Runtime.getRuntime().addShutdownHook(new Thread(() -> vtx.close(event -> {
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
