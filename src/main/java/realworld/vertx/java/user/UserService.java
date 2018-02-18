package realworld.vertx.java.user;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;

import java.util.HashMap;

/**
 * @author Samer Kanjo
 * @since 0.2.0 2/12/18 10:16 PM
 */
@ProxyGen
public interface UserService {

  static UserService create(JDBCClient dbClient, HashMap<SqlQuery, String> sqlQueries, Handler<AsyncResult<UserService>> readyHandler) {
    return new JdbcUserService(dbClient, sqlQueries, readyHandler);
  }

  static UserService createProxy(Vertx vertx, String address) {
    return new UserServiceVertxEBProxy(vertx, address);
  }

  @Fluent
  UserService register(String username, String password, String email, Handler<AsyncResult<Void>> resultHandler);

  @Fluent
  UserService get(String username, Handler<AsyncResult<JsonObject>> resultHandler);

  @Fluent
  UserService all(Handler<AsyncResult<JsonArray>> resultHandler);

}
