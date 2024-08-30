package edu.dandaev_it;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.dandaev_it.util.ConnectionManager;

public class Main {

	public static void main(String[] args) {
		String insert_into_authors_table = """
				INSERT INTO
					authors (
								first_name,
								last_name,
								date_of_birth,
								date_of_death,
								nationality,
								biography,
								email,
								website,
								gender,
								profile_picture
							)
				VALUES
					('Vladislav', 'Potterrey', '1975-05-30', NULL, 'Irish', 'Michael is known for his thrillers...', 'michael.williams@example.com', 'http://michaelwilliams.com', 'Male', 'http://michaelwilliams.com/pic5.jpg'),
					('Natalie', 'Bredberry', '1982-09-15', NULL, 'American', 'Emily is a celebrated novelist...', 'emily.johnson@example.com', 'http://emilyjohnson.com', 'Female', 'http://emilyjohnson.com/pic2.jpg'),
					('Mark', 'La-Franch', '1968-03-22', NULL, 'British', 'James has authored several bestsellers...', 'james.brown@example.com', 'http://jamesbrown.com', 'Male', 'http://jamesbrown.com/pic3.jpg'),
					('Uchicha', 'Orls', '1990-11-07', NULL, 'Spanish', 'Olivia writes poetry and short stories...', 'olivia.martinez@example.com', 'http://oliviamartinez.com', 'Female', 'http://oliviamartinez.com/pic4.jpg'),
					('Jeff', 'Shmutz', '1972-02-14', NULL, 'Canadian', 'David is known for his historical novels...', 'david.smith@example.com', 'http://davidsmith.com', 'Male', 'http://davidsmith.com/pic1.jpg');
				""";

		try (Connection connection = ConnectionManager.open()) {
			System.out.println("\033[0;32mConnection established successfully!\033[0m");

			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);

			statement.executeUpdate(insert_into_authors_table, Statement.RETURN_GENERATED_KEYS);
			ResultSet generatedKeys = statement.getGeneratedKeys();

			while (generatedKeys.next()) {
				System.out.println("generated key: " + generatedKeys.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
