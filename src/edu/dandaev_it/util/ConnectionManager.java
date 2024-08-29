package edu.dandaev_it.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManager {
	private static final String DATABASE_URL_KEY = "database.url";
	private static final String PASSWORD_KEY = "database.password";
	private static final String USER_NAME_KEY = "database.user";

	// до java 1.8
	static {
		loadDriver();
	}

	private static void loadDriver() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private ConnectionManager() {
	}

	public static Connection open() {
		try {
			return DriverManager.getConnection(
					PropertiesUtil.get(DATABASE_URL_KEY),
					PropertiesUtil.get(USER_NAME_KEY),
					PropertiesUtil.get(PASSWORD_KEY));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
