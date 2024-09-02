package edu.dandaev_it;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import edu.dandaev_it.util.ConnectionManager;

public class Main {

	public static void main(String[] args) throws SQLException {
		String delete_flight_by_id = """
				delete
				from
					flight
				where
					id = ?
				""";
		Long flight_id = 3L;

		String delete_referenced_ticket = """
				delete
				from
					ticket
				where
					flight_id = ?
				""";
		Connection connection = null;
		PreparedStatement preparedStatement_1 = null;
		PreparedStatement preparedStatement_2 = null;

		try {
			connection = ConnectionManager.open();
			connection.setAutoCommit(false);

			preparedStatement_1 = connection.prepareStatement(delete_flight_by_id);
			preparedStatement_2 = connection.prepareStatement(delete_referenced_ticket);

			preparedStatement_2.setLong(1, flight_id);
			preparedStatement_2.executeUpdate();

			// моделирование ситуации возникновения exception
			if (true) {
				throw new RuntimeException("Something went wrong");
			}
			preparedStatement_1.setLong(1, flight_id);
			preparedStatement_1.executeUpdate();
			
			connection.commit();
		} catch (Exception exc) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e) {
					throw e;
				}
			}
		} finally {
			if (preparedStatement_2 != null) {
				preparedStatement_2.close();
			}
			if (preparedStatement_1 != null) {
				preparedStatement_1.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}
}
