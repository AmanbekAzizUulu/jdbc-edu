package edu.dandaev_it;

import java.sql.Connection;
import java.sql.SQLException;

import edu.dandaev_it.util.ConnectionManager;

public class Main {

	public static void main(String[] args) {
		try (Connection connection = ConnectionManager.open()) {
			System.out.println("Connection established successfully!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
