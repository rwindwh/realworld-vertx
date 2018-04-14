package realworld.vertx.java.user;

import com.google.common.base.CharMatcher;
import com.google.common.base.Strings;
import io.vertx.core.json.JsonObject;
import realworld.vertx.java.validation.Errors;
import realworld.vertx.java.validation.Validate;
import realworld.vertx.java.validation.ValidationException;

/**
 * @author Samer Kanjo
 * @since 0.2.0 4/13/18 9:54 AM
 */
class AuthenticatedUser {

  static Builder newBuilder() {
    return new Builder();
  }

  private final RegisteredUser user;
  private final String token;

  private AuthenticatedUser(Builder b) {
    user = b.user;
    token = b.token;
  }

  void writeTo(JsonObject json) {
    final JsonObject userObj = new JsonObject();
    json.put("user", userObj);

    userObj.put("email", user.email());
    userObj.put("token", token);
    userObj.put("username", user.username());

    if (user.biography().isEmpty()) {
      userObj.putNull("bio");
    } else {
      userObj.put("bio", user.biography());
    }

    if (user.imageUrl().isEmpty()) {
      userObj.putNull("image");
    } else {
      userObj.put("image", user.imageUrl());
    }
  }

  RegisteredUser user() {
    return user;
  }

  String token() {
    return token;
  }

  static class Builder {

    private RegisteredUser user;
    private String token;

    private Builder() {
      clear();
    }

    boolean isInitialized() {
      return user != null
        && !token.isEmpty();
    }

    Builder mergeFrom(AuthenticatedUser other) {
      user(other.user);
      token(other.token);

      return this;
    }

    Builder clear() {
      user = null;
      token = "";

      return this;
    }

    AuthenticatedUser build() {
      final AuthenticatedUser provisional = new AuthenticatedUser(this);

      final Errors errors = new Errors();
      Validate.required(errors, provisional.user(), "user");
      Validate.required(errors, provisional.token(), "token");

      if (!errors.empty()) {
        throw new ValidationException(errors);
      }

      return provisional;
    }

    Builder user(RegisteredUser value) {
      user = value;
      return this;
    }

    Builder token(String value) {
      token = CharMatcher.whitespace().trimFrom(Strings.nullToEmpty(value));
      return this;
    }

  }

}
