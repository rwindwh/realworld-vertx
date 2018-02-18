package realworld.vertx.java.user;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.serviceproxy.ServiceBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

/**
 * @author Samer Kanjo
 * @since 0.2.0 2/12/18 11:23 PM
 */
public class UserServiceVerticle extends AbstractVerticle {

  private static Logger LOGGER = LoggerFactory.getLogger(UserServiceVerticle.class);

  private static final String CONFIG_WIKIDB_JDBC_URL = "wikidb.jdbc.url";
  private static final String CONFIG_WIKIDB_JDBC_DRIVER_CLASS = "wikidb.jdbc.driver_class";
  private static final String CONFIG_WIKIDB_JDBC_MAX_POOL_SIZE = "wikidb.jdbc.max_pool_size";

  @Override
  public void start(Future<Void> startFuture) throws Exception {
    LOGGER.info("Starting user service verticle...");

    final HashMap<SqlQuery, String> sqlQueries = loadSqlQueries();

    final JDBCClient dbClient = JDBCClient.createShared(vertx, new JsonObject()
      .put("url", config().getString(CONFIG_WIKIDB_JDBC_URL, "jdbc:hsqldb:file:db/user"))
      .put("driver_class", config().getString(CONFIG_WIKIDB_JDBC_DRIVER_CLASS, "org.hsqldb.jdbcDriver"))
      .put("max_pool_size", config().getInteger(CONFIG_WIKIDB_JDBC_MAX_POOL_SIZE, 30)));

    UserService.create(dbClient, sqlQueries, ready -> {
      if (ready.succeeded()) {
        ServiceBinder binder = new ServiceBinder(vertx);
        binder.setAddress("user.queue").register(UserService.class, ready.result());
        startFuture.complete();
        LOGGER.info("Successfully started user service verticle.");
      } else {
        startFuture.fail(ready.cause());
        LOGGER.info("Failed to start user service verticle.");
      }
    });
  }

  private HashMap<SqlQuery, String> loadSqlQueries() throws IOException {
    final InputStream queriesInputStream = getClass().getResourceAsStream("/db-queries.properties");

    final Properties queriesProps = new Properties();
    queriesProps.load(queriesInputStream);
    queriesInputStream.close();

    final HashMap<SqlQuery, String> sqlQueries = new HashMap<>();
    sqlQueries.put(SqlQuery.CREATE_USER_TABLE, queriesProps.getProperty("create-user-table"));
    sqlQueries.put(SqlQuery.INSERT_USER, queriesProps.getProperty("insert-user"));
    sqlQueries.put(SqlQuery.SELECT_USER, queriesProps.getProperty("select-user"));
    sqlQueries.put(SqlQuery.SELECT_ALL_USERS, queriesProps.getProperty("select-all-users"));

    LOGGER.debug("Loaded the following sql queries...");
    LOGGER.debug(sqlQueries.toString());

    return sqlQueries;
  }
}
