package realworld.vertx.java.validation;

/**
 * @author Samer Kanjo
 * @since 0.2.0 4/14/18 11:57 AM
 */
public class Validate {

  public static void required(Errors errors, String value, String name) {
    if (value == null || value.isEmpty()) {
      errors.add(name, "is required");
    }
  }

  public static void required(Errors errors, long value, String name) {
    if (value <= 0) {
      errors.add(name, "is required");
    }
  }

  public static void required(Errors errors, Object value, String name) {
    if (value == null) {
      errors.add(name, "is required");
    }
  }

}
