package edu.dandaev_it;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.dandaev_it.util.ConnectionManager;

public class Main {

	public static void main(String[] args) {
		String delete_flight_by_id = """
				delete
				from
					flight
				where
					id = ?
				""";
		Long flight_id = 3L;

		String select_all_from_flight_table = """
				select
					id
				from
					flight
				""";
		try (Connection connection = ConnectionManager.open();
				PreparedStatement preparedStatement_1 = connection.prepareStatement(delete_flight_by_id);
				PreparedStatement preparedStatement_2 =  connection.prepareStatement(select_all_from_flight_table)) {

					// Cannot delete or update a parent row: a foreign key constraint fails
					preparedStatement_1.setLong(1, flight_id);


					preparedStatement_1.executeUpdate();
					ResultSet resultSet = preparedStatement_2.executeQuery();
					while (resultSet.next()) {
						System.out.println("id = " + resultSet.getInt(1));
					}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
