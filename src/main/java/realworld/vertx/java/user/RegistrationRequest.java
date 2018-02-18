package realworld.vertx.java.user;

import io.vertx.core.json.JsonObject;

/**
 * @author Samer Kanjo
 * @since 0.2.0 1/2/18 2:17 PM
 */
class RegistrationRequest {

  static Builder builder() {
    return new Builder();
  }

  private String username;
  private String password;
  private String email;

  private RegistrationRequest(Builder b) {
    username = b.username;
    password = b.password;
    email = b.email;
  }

  String username() {
    return username;
  }

  String password() {
    return password;
  }

  String email() {
    return email;
  }

  public JsonObject toJson() {
    final JsonObject json = new JsonObject();
    json.put("username", username);
    json.put("password", password);
    json.put("email", email);
    return json;
  }

  @Override
  public String toString() {
    return "RegistrationRequest{" +
      "username='" + username + '\'' +
      ", password='********'" +
      ", email='" + email + '\'' +
      '}';
  }

  static class Builder {

    private String username;
    private String password;
    private String email;

    private Builder() {
    }

    RegistrationRequest build() {
      validate();
      return new RegistrationRequest(this);
    }

    void validate() {
    }

    Builder username(String value) {
      username = value;
      return this;
    }

    Builder password(String value) {
      password = value;
      return this;
    }

    Builder email(String value) {
      email = value;
      return this;
    }

    Builder fromJson(JsonObject json) {
      if (json.getValue("username") instanceof String) {
        username = (String) json.getValue("username");
      }
      if (json.getValue("password") instanceof String) {
        password = (String) json.getValue("password");
      }
      if (json.getValue("email") instanceof String) {
        email = (String) json.getValue("email");
      }
      return this;
    }

  }

}
