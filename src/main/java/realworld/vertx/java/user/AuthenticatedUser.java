package realworld.vertx.java.user;

import com.google.common.base.CharMatcher;
import com.google.common.base.Strings;
import io.vertx.core.json.JsonObject;

/**
 * @author Samer Kanjo
 * @since 0.2.0 4/13/18 9:54 AM
 */
public class AuthenticatedUser {

  public static Builder newBuilder() {
    return new Builder();
  }

  private final RegisteredUser user;
  private final String token;

  private AuthenticatedUser(Builder b) {
    user = b.user;
    token = b.token;
  }

  public void writeTo(JsonObject json) {
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

  public RegisteredUser user() {
    return user;
  }

  public String token() {
    return token;
  }

  public static class Builder {

    private RegisteredUser user;
    private String token;

    private Builder() {
      clear();
    }

    public boolean isInitialized() {
      return user != null
        && !token.isEmpty();
    }

    public Builder mergeFrom(AuthenticatedUser other) {
      user(other.user);
      token(other.token);

      return this;
    }

    public Builder clear() {
      user = null;
      token = "";

      return this;
    }

    public AuthenticatedUser build() {
      if (user == null) {
        throw new IllegalArgumentException("user is required");
      }
      if (token.isEmpty()) {
        throw new IllegalArgumentException("token is required");
      }

      return new AuthenticatedUser(this);
    }

    public Builder user(RegisteredUser value) {
      user = value;
      return this;
    }

    public Builder token(String value) {
      token = CharMatcher.whitespace().trimFrom(Strings.nullToEmpty(value));
      return this;
    }

  }

}
