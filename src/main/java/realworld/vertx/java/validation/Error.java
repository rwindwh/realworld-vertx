package realworld.vertx.java.validation;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * @author Samer Kanjo
 * @since 0.2.0 1/28/18 9:07 PM
 */
public class Error {

  private String name;
  private String description;

  public Error(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public String name() {
    return name;
  }

  public String description() {
    return description;
  }

  public void writeTo(JsonObject json) {
    json.put(name, new JsonArray().add(description));
  }

}
