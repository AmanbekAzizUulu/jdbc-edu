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

			String insert_into_authors_table ="""
					INSERT INTO authors
					    (first_name, last_name, date_of_birth, date_of_death, nationality, biography, email, website, gender, profile_picture)
					VALUES
					    ( 'Leo', 'Tolstoy', '1828-09-09', '1910-11-20', 'Russian', 'Russian writer known for War and Peace and Anna Karenina.', 'leo.tolstoy@example.com', 'http://tolstoy.com', 'Male', 'tolstoy.jpg'),
					    ('Jane', 'Austen', '1775-12-16', '1817-07-18', 'British', 'English novelist known primarily for her six major novels.', 'jane.austen@example.com', 'http://austen.com', 'Female', 'austen.jpg');
					""";
			statement.execute(insert_into_authors_table);


		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
