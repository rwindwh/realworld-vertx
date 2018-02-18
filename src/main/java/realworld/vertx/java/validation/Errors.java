package realworld.vertx.java.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Samer Kanjo
 * @since 0.2.0 1/28/18 11:39 PM
 */
public class Errors {

  private final List<Error> errors = new ArrayList<>();

  public void add(Error e) {


  }

  public boolean empty() {
    return errors.size() == 0;
  }

}
