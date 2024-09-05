package edu.dandaev_it.util;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class ConnectionManager {
  private static final String DATABASE_URL_KEY = "database.url";
  private static final String PASSWORD_KEY = "database.password";
  private static final String USER_NAME_KEY = "database.user";
  private static final String CONNECTION_POOL_SIZE_KEY = "database.connection_pool_size";

  private static BlockingQueue<Connection> connectionPool;
  private static List<Connection> sourceConnections;
  private static int DEFAULT_POOL_SIZE = 10;

  private ConnectionManager() {
  }

  // до java 1.8
  static {
    loadDriver();
    initConnectionPool();
  }

  private static void loadDriver() {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  private static void initConnectionPool() {
    var poolSize = PropertiesUtil.get(CONNECTION_POOL_SIZE_KEY);
    var size = poolSize == null ? DEFAULT_POOL_SIZE : Integer.parseInt(poolSize);
    connectionPool = new ArrayBlockingQueue<Connection>(size);
    sourceConnections = new ArrayList<Connection>(size);
    for (int i = 0; i < size; i++) {
      var connection = open();
      var proxyConnection = (Connection) Proxy.newProxyInstance(ConnectionManager.class.getClassLoader(),
          new Class[] { Connection.class },
          (proxy, method, args) -> method.getName().equals("close")
              ? connectionPool.add((Connection) proxy)
              : method.invoke(connection, args));
      connectionPool.add(proxyConnection);
      sourceConnections.add(connection);
    }
  }

  private static Connection open() {
    try {
      return DriverManager.getConnection(
          PropertiesUtil.get(DATABASE_URL_KEY),
          PropertiesUtil.get(USER_NAME_KEY),
          PropertiesUtil.get(PASSWORD_KEY));

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static Connection get() {
    try {
      return connectionPool.take();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public static void closePool() {
    for (Connection connection : sourceConnections) {
      try {
        connection.close();
      } catch (SQLException e) {
        throw new RuntimeException("Failed to close connection", e);
      }
    }
  }
}
