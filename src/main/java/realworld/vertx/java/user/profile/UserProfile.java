package realworld.vertx.java.user.profile;

import com.google.common.base.CharMatcher;
import com.google.common.base.Strings;
import io.vertx.core.json.JsonObject;

/**
 * @author Samer Kanjo
 * @since 0.2.0 3/31/18 3:44 PM
 */
public class UserProfile {

  public static Builder newBuilder() {
    return new Builder();
  }

  private String username;
  private String biography;
  private String imageUrl;
  private boolean following;

  public UserProfile() {
  }

  private UserProfile(Builder b) {
    this.username = b.username;
    this.biography = b.biography;
    this.imageUrl = b.imageUrl;
    this.following = b.following;
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

  public boolean following() {
    return following;
  }

  public void writeTo(JsonObject json) {
    final JsonObject profileJson = new JsonObject();
    json.put("profile", profileJson);

    if (!username.isEmpty()) {
      profileJson.put("username", username);
    }
    if (!biography.isEmpty()) {
      profileJson.put("bio", biography);
    }
    if (!imageUrl.isEmpty()) {
      profileJson.put("image", imageUrl);
    }
    profileJson.put("following", following);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    UserProfile that = (UserProfile) o;

    if (following != that.following) return false;
    if (!username.equals(that.username)) return false;
    if (!biography.equals(that.biography)) return false;
    return imageUrl.equals(that.imageUrl);
  }

  @Override
  public int hashCode() {
    int result = username.hashCode();
    result = 31 * result + biography.hashCode();
    result = 31 * result + imageUrl.hashCode();
    result = 31 * result + (following ? 1 : 0);
    return result;
  }

  @Override
  public String toString() {
    return "UserProfile{" +
      "username='" + username + '\'' +
      ", biography='" + biography + '\'' +
      ", imageUrl='" + imageUrl + '\'' +
      ", following=" + following +
      '}';
  }

  public static class Builder {

    private String username;
    private String biography;
    private String imageUrl;
    private boolean following;

    private Builder() {
      clear();
    }

    public boolean isInitialized() {
      return !username.isEmpty();
    }

    public Builder mergeFrom(UserProfile other) {
      username(other.username);
      biography(other.biography);
      imageUrl(other.imageUrl);
      following(other.following);

      return this;
    }

    public Builder clear() {
      username = "";
      biography = "";
      imageUrl = "";
      following = false;

      return this;
    }

    public UserProfile build() {
      if (username.isEmpty()) {
        throw new IllegalArgumentException("username is required");
      }

      return new UserProfile(this);
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

    public Builder following(boolean value) {
      following = value;
      return this;
    }
  }

}
