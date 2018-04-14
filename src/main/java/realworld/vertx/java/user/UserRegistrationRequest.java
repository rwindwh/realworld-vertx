package realworld.vertx.java.user;

import com.google.common.base.CharMatcher;
import com.google.common.base.Strings;
import io.vertx.core.json.JsonObject;

/**
 * @author Samer Kanjo
 * @since 0.2.0 4/13/18 10:50 AM
 */
class UserRegistrationRequest {

  static Builder newBuilder() {
    return new Builder();
  }

  static UserRegistrationRequest parseFrom(JsonObject json) {
    final Builder b = newBuilder();

    if (json.getValue("username") instanceof String) {
      b.username((String) json.getValue("username"));
    }

    if (json.getValue("email") instanceof String) {
      b.email((String) json.getValue("email"));
    }

    if (json.getValue("password") instanceof String) {
      b.password((String) json.getValue("password"));
    }

    return b.build();
  }

  private String username;
  private String email;
  private String hashedPassword;

  private UserRegistrationRequest(Builder b) {
    this.username = b.username;
    this.hashedPassword = b.hashedPassword;
    this.email = b.email;
  }

  String username() {
    return username;
  }

  String email() {
    return email;
  }

  String hashedPassword() {
    return hashedPassword;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    UserRegistrationRequest that = (UserRegistrationRequest) o;

    if (!username.equals(that.username)) return false;
    if (!email.equals(that.email)) return false;
    return hashedPassword.equals(that.hashedPassword);
  }

  @Override
  public int hashCode() {
    int result = username.hashCode();
    result = 31 * result + email.hashCode();
    result = 31 * result + hashedPassword.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "UserIdentity{" +
      "username='" + username + '\'' +
      ", email='" + email + '\'' +
      ", hashedPassword='" + hashedPassword + '\'' +
      '}';
  }

  static class Builder {

    private String username;
    private String email;
    private String hashedPassword;

    private Builder() {
      clear();
    }

    boolean isInitialized() {
      return !username.isEmpty()
        && !email.isEmpty()
        && !hashedPassword.isEmpty();
    }

    Builder mergeFrom(UserRegistrationRequest other) {
      username(other.username);
      email(other.email);
      password(other.hashedPassword);

      return this;
    }

    Builder clear() {
      username = "";
      email = "";
      hashedPassword = "";

      return this;
    }

    UserRegistrationRequest build() {
      if (username.isEmpty()) {
        throw new IllegalArgumentException("username is required");
      }
      if (email.isEmpty()) {
        throw new IllegalArgumentException("email is required");
      }
      if (hashedPassword.isEmpty()) {
        throw new IllegalArgumentException("password is required");
      }

      return new UserRegistrationRequest(this);
    }

    Builder username(String value) {
      username = CharMatcher.whitespace().trimFrom(Strings.nullToEmpty(value));
      return this;
    }

    Builder email(String value) {
      email = CharMatcher.whitespace().trimFrom(Strings.nullToEmpty(value));
      return this;
    }

    Builder password(String value) {
      hashedPassword = Passwords.hash(CharMatcher.whitespace().trimFrom(Strings.nullToEmpty(value)));
      return this;
    }

  }

}
