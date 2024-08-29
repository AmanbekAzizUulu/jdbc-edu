package edu.dandaev_it;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import edu.dandaev_it.util.ConnectionManager;

public class Main {

	public static void main(String[] args) {
		try (Connection connection = ConnectionManager.open()) {
			System.out.println("\033[0;32mConnection established successfully!\033[0m");
			Statement statement = connection.createStatement();

			 // SQL-запрос для обновления данных
			 String update_row_authors_table = """
					update
						authors
					set
						first_name = 'Aibek',
						last_name = 'Dandaev'
					where
						author_id = 26;
			 """;

			statement.executeUpdate(update_row_authors_table);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
