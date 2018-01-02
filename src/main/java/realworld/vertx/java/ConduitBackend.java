package realworld.vertx.java;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;

/**
 * @author Samer Kanjo
 * @since 0.1.0 1/1/18 12:53 PM
 */
public class ConduitBackend {

  public static void main(String[] args) {
    try {
      final VertxOptions vopt = new VertxOptions();
      vopt.setBlockedThreadCheckInterval(20000);

      final Vertx v = Vertx.vertx(vopt);

      final JsonObject conf = new JsonObject().put("listen", 8080);

      final DeploymentOptions dopt = new DeploymentOptions();
      dopt.setConfig(conf);

      v.deployVerticle(new HttpServer(), dopt);

      Runtime.getRuntime().addShutdownHook(new Thread(() -> v.close(event -> {
        if (event.succeeded()) {
          System.out.println("Shutdown successful");
        } else {
          System.out.println("Failed to shutdown");
        }
      })));

    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

}
