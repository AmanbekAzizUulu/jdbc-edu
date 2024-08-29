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

			 // SQL-запрос для вставки данных
			 String insert_into_authors_table = """
						INSERT INTO
							authors (first_name, last_name, date_of_birth, date_of_death, nationality, biography, email, website, gender, profile_picture) 
						VALUES
						 	('Mark', 'Twain', '1835-11-30', '1910-04-21', 'American', 'American writer known for The Adventures of Tom Sawyer and Adventures of Huckleberry Finn.', 'mark.twain@example.com', 'http://twain.com', 'Male', 'twain.jpg');
						 """;

			statement.executeUpdate(insert_into_authors_table);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
