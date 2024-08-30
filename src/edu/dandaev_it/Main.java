package edu.dandaev_it;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.dandaev_it.util.ConnectionManager;

public class Main {

	public static void main(String[] args) {
		Long flightId = 2L;

		try (Connection connection = ConnectionManager.open()) {
			System.out.println("\033[0;32mConnection established successfully!\033[0m");

			List<Long> ticketsByFlightId = getTicketsByFlightId(flightId);
			System.out.println("tickets with flight_id = " + ticketsByFlightId);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static List<Long> getTicketsByFlightId(Long flightId) throws SQLException {
		List<Long> selectedId = new ArrayList<Long>();
		String select_ticket_by_flight_id = """
					select
						id
					from
						ticket
					where
						flight_id = ?
				""";
		try (Connection connection = ConnectionManager.open();
				PreparedStatement preparedStatement = connection.prepareStatement(select_ticket_by_flight_id)) {

			preparedStatement.setLong(1, flightId);

			var resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				selectedId.add(resultSet.getObject(1, Long.class)); // на случай если resultSet будет содержать null
			}
		}
		return selectedId;
	}
}
