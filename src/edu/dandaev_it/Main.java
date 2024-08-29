package edu.dandaev_it;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import edu.dandaev_it.util.ConnectionManager;

public class Main {

	public static void main(String[] args) {
		String drop_table_if_exists__authors = "drop table if exists authors;";
		String create_table__authors = """
				CREATE TABLE authors (
					author_id INT AUTO_INCREMENT PRIMARY KEY,
					first_name VARCHAR(100) NOT NULL,
					last_name VARCHAR(100) NOT NULL,
					date_of_birth DATE,
					date_of_death DATE,
					nationality VARCHAR(50),
					biography TEXT,
					email VARCHAR(255),
					website VARCHAR(255),
					gender ENUM('Male', 'Female', 'Other'),
					profile_picture VARCHAR(255)
				);
				""";
		String drop_table_if_exists__genres = "drop table if exists genres;";
		String create_table__genres = """
				CREATE TABLE genres (
					genre_id INT AUTO_INCREMENT PRIMARY KEY,
					genre_name VARCHAR(50) NOT NULL
				);
				""";
		String drop_table_if_exists__books = "drop table if exists books;";
		String create_table__books = """
				CREATE TABLE books (
					book_id INT AUTO_INCREMENT PRIMARY KEY,
					title VARCHAR(250) NOT NULL,
					publication_date DATE,
					author_id INT NOT NULL,
					genre_id INT NOT NULL,
					FOREIGN KEY (author_id) REFERENCES authors(author_id),
					FOREIGN KEY (genre_id) REFERENCES genres(genre_id)
				);
				""";

		try (Connection connection = ConnectionManager.open()) {
			System.out.println("\033[0;32mConnection established successfully!\033[0m");

			Statement statement = connection.createStatement();

			statement.execute(drop_table_if_exists__authors);
			statement.execute(create_table__authors);

			statement.execute(drop_table_if_exists__genres);
			statement.execute(create_table__genres);

			statement.execute(drop_table_if_exists__books);
			statement.execute(create_table__books);

			System.out.println("Tables are successfully created!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
