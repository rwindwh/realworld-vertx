package realworld.vertx.java.user;

import com.google.common.base.CharMatcher;
import com.google.common.base.Strings;

/**
 * @author Samer Kanjo
 * @since 0.2.0 4/13/18 3:59 PM
 */
class RegisteredUser {

  private final long id;
  private final String email;
  private final String hashedPassword;
  private final String username;
  private final String biography;
  private final String imageUrl;

  static Builder newBuilder() {
    return new Builder();
  }

  private RegisteredUser(Builder b) {
    id = b.id;
    email = b.email;
    hashedPassword = b.hashedPassword;
    username = b.username;
    biography = b.biography;
    imageUrl = b.imageUrl;
  }

  public long id() {
    return id;
  }

  public String email() {
    return email;
  }

  public String hashedPassword() {
    return hashedPassword;
  }

  public String username() {
    return username;
  }

  public String biography() {
    return biography;
  }

  public String imageUrl() {
    return imageUrl;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    RegisteredUser that = (RegisteredUser) o;

    if (id != that.id) return false;
    if (!email.equals(that.email)) return false;
    if (!hashedPassword.equals(that.hashedPassword)) return false;
    if (!username.equals(that.username)) return false;
    if (biography != null ? !biography.equals(that.biography) : that.biography != null) return false;
    return imageUrl != null ? imageUrl.equals(that.imageUrl) : that.imageUrl == null;
  }

  @Override
  public int hashCode() {
    int result = (int) (id ^ (id >>> 32));
    result = 31 * result + email.hashCode();
    result = 31 * result + hashedPassword.hashCode();
    result = 31 * result + username.hashCode();
    result = 31 * result + (biography != null ? biography.hashCode() : 0);
    result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "VolatileUser{" +
      "id=" + id +
      ", email='" + email + '\'' +
      ", hashedPassword='" + hashedPassword + '\'' +
      ", username='" + username + '\'' +
      ", biography='" + biography + '\'' +
      ", imageUrl='" + imageUrl + '\'' +
      '}';
  }

  static class Builder {

    private long id;
    private String email;
    private String hashedPassword;
    private String username;
    private String biography;
    private String imageUrl;

    private Builder() {
      clear();
    }

    public boolean isInitialized() {
      return id > 0
        && !email.isEmpty()
        && !hashedPassword.isEmpty()
        && !username.isEmpty();
    }

    Builder mergeFrom(RegisteredUser other) {
      id(other.id);
      email(other.email);
      hashedPassword(other.hashedPassword);
      username(other.username);
      biography(other.biography);
      imageUrl(other.imageUrl);

      return this;
    }

    Builder clear() {
      id = -1;
      email = "";
      hashedPassword = "";
      username = "";
      biography = "";
      imageUrl = "";

      return this;
    }

    public RegisteredUser build() {
      if (id <= 0) {
        throw new IllegalArgumentException("id is required");
      }
      if (email.isEmpty()) {
        throw new IllegalArgumentException("email is required");
      }
      if (hashedPassword.isEmpty()) {
        throw new IllegalArgumentException("hashedPassword is required");
      }
      if (username.isEmpty()) {
        throw new IllegalArgumentException("username is required");
      }

      return new RegisteredUser(this);
    }

    public Builder id(long value) {
      id = value > 0 ? value : -1;
      return this;
    }

    public Builder email(String value) {
      email = CharMatcher.whitespace().trimFrom(Strings.nullToEmpty(value));
      return this;
    }

    public Builder hashedPassword(String value) {
      hashedPassword = CharMatcher.whitespace().trimFrom(Strings.nullToEmpty(value));
      return this;
    }

    public Builder username(String value) {
      username = CharMatcher.whitespace().trimFrom(Strings.nullToEmpty(value));
      return this;
    }

    public Builder biography(String value) {
      biography = CharMatcher.whitespace().trimFrom(Strings.nullToEmpty(value));
      return this;
    }

    public Builder imageUrl(String value) {
      imageUrl = CharMatcher.whitespace().trimFrom(Strings.nullToEmpty(value));
      return this;
    }

  }

}
