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

			// к сожалению множественный запрос в базу данных mysql через execute() не будет работать
			// поэтому мне пришлось разбить каждый запрос на отдельные единицы (подробнее читай в README.md файле)
			String insert_into_authors_table_1 = "USE literature;";
			String insert_into_authors_table_2 =
					"""
							INSERT INTO authors
					       	    (first_name, last_name, date_of_birth, date_of_death, nationality, biography, email, website, gender, profile_picture)
					      	VALUES
					           ('Mark', 'Twain', '1835-11-30', '1910-04-21', 'American', 'American writer known for The Adventures of Tom Sawyer and Adventures of Huckleberry Finn.', 'mark.twain@example.com', 'http://twain.com', 'Male', 'twain.jpg');
					""";

			String insert_into_authors_table_3 =
					"""
							INSERT INTO authors
					           (first_name, last_name, date_of_birth, date_of_death, nationality, biography, email, website, gender, profile_picture)
					       	VALUES
					           ('Virginia', 'Woolf', '1882-01-25', '1941-03-28', 'British', 'English writer known for Mrs Dalloway and To the Lighthouse.', 'virginia.woolf@example.com', 'http://woolf.com', 'Female', 'woolf.jpg'),
					           ('Gabriel', 'Garcia Marquez', '1927-03-06', '2014-04-17', 'Colombian', 'Colombian novelist known for One Hundred Years of Solitude.', 'gabriel.garcia@example.com', 'http://garcia-marquez.com', 'Male', 'garcia.jpg');
					""";
			statement.execute(insert_into_authors_table_1);
			statement.execute(insert_into_authors_table_2);
			statement.execute(insert_into_authors_table_3);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
