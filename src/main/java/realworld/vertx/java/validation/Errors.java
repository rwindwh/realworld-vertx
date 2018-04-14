package realworld.vertx.java.validation;

import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Samer Kanjo
 * @since 0.2.0 1/28/18 11:39 PM
 */
public class Errors {

  private final List<Error> errors = new ArrayList<>();

  public void add(String name, String description) {
    errors.add(new Error(name, description));
  }

  public void add(Error e) {
    errors.add(e);
  }

  public void addAll(Errors e) {
    errors.addAll(e.errors);
  }

  public boolean empty() {
    return errors.size() == 0;
  }

  public void writeTo(JsonObject json) {
    final JsonObject errorObj = new JsonObject();
    json.put("errors", errorObj);

    for (Error e : errors) {
      e.writeTo(errorObj);
    }
  }

}
