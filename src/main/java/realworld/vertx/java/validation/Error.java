package realworld.vertx.java.validation;

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
}
