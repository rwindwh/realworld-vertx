package realworld.vertx.java.user;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import realworld.vertx.java.user.identity.UserIdentity;
import realworld.vertx.java.user.identity.UserIdentityStore;
import realworld.vertx.java.user.profile.UserProfile;
import realworld.vertx.java.user.profile.UserProfileStore;

/**
 * @author Samer Kanjo
 * @since 0.2.0 3/31/18 9:29 AM
 */
public class LocalUserService implements UserService {

  private final UserIdentityStore identityStore;
  private final UserProfileStore profileStore;

  public LocalUserService(UserIdentityStore identityStore, UserProfileStore profileStore) {
    this.identityStore = identityStore;
    this.profileStore = profileStore;
  }

  @Override
  public UserService register(UserIdentity newIdentity, Handler<AsyncResult<Void>> resultHandler) {
    final String userKey = newIdentity.username();

    if (identityStore.contains(userKey)) {
      resultHandler.handle(Future.failedFuture("user already registered"));

    } else {
      final UserProfile newProfile = UserProfile.newBuilder().username(newIdentity.username()).build();

      identityStore.add(newIdentity);
      profileStore.add(newProfile);

      resultHandler.handle(Future.succeededFuture());
    }

    return this;
  }

  @Override
  public UserService get(String username, Handler<AsyncResult<UserProfile>> resultHandler) {
    if (profileStore.contains(username)) {
      resultHandler.handle(Future.succeededFuture(profileStore.get(username)));
    } else {
      resultHandler.handle(Future.failedFuture("user not found"));
    }

    return this;
  }

}
