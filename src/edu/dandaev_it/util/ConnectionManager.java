package edu.dandaev_it.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManager {
	private static final String DATABASE_NAME = "jdbc:mysql://localhost:3306/university";
	private static final String PASSWORD = "root";
	private static final String USER_NAME = "root";

	// до java 1.8
	static {
		loadDriver();
	}

	private static void loadDriver() {
		try {
			Class.forName("com.mysql.cj.jdbc");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private ConnectionManager() {
	}

	public static Connection open() {
		try {
			return DriverManager.getConnection(DATABASE_NAME, USER_NAME, PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
