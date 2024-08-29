package edu.dandaev_it;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.dandaev_it.util.ConnectionManager;

public class Main {

	public static void main(String[] args) {
		String select_all_from_authors_table = """
						select
							*
						from
							authors;
				""";

		try (Connection connection = ConnectionManager.open()) {
			System.out.println("\033[0;32mConnection established successfully!\033[0m");

			Statement statement = connection.createStatement();

			statement.execute(select_all_from_authors_table);

			try (ResultSet resultSet = statement.getResultSet()) {
				System.out.println("Some data selected from 'authors' table");
				while (resultSet.next()) {
					System.out.println("id: " + resultSet.getInt("author_id")
							+ "\t| first_name: " + resultSet.getString("first_name")
							+ "\t| last_name: " + resultSet.getString("last_name")
							+ "\t\t| date_of_birth: " + resultSet.getDate("date_of_birth"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
