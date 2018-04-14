package realworld.vertx.java.validation;

/**
 * @author Samer Kanjo
 * @since 0.2.0 4/14/18 10:30 AM
 */
public class ValidationException extends RuntimeException {

  private final Errors errors;

  public ValidationException(Errors errors) {
    this(errors, null);
  }

  public ValidationException(Errors errors, String message) {
    this(errors, message, null);
  }

  public ValidationException(Errors errors, String message, Throwable cause) {
    super(message, cause, true, false);
    this.errors = errors;
  }

  public Errors errors() {
    return errors;
  }

}
