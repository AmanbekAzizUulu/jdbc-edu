package edu.dandaev_it;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import edu.dandaev_it.util.ConnectionManager;

public class Main {

	public static void main(String[] args) {
		LocalDateTime start = LocalDate.of(2020, 06, 1).atStartOfDay();
		LocalDateTime end = LocalDateTime.now();

		try (Connection connection = ConnectionManager.open()) {
			System.out.println("\033[0;32mConnection established successfully!\033[0m");

			List<Long> flightsBetween = getFlightsBetween(start, end);
			System.out.println("flights between " + start + " and " + end + " : " + flightsBetween);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static List<Long> getFlightsBetween(LocalDateTime start, LocalDateTime end) throws SQLException {
		List<Long> selectedId = new ArrayList<Long>();
		String select_flights_between = """
						select
							id
						from
							flight
						where
							departure_date between ? and ?;

				""";

		try (Connection connection = ConnectionManager.open();
				PreparedStatement preparedStatement = connection.prepareStatement(select_flights_between)) {

			preparedStatement.setFetchSize(5);
			preparedStatement.setQueryTimeout(10);
			preparedStatement.setMaxRows(5);

			System.out.println(preparedStatement);
			preparedStatement.setTimestamp(1, java.sql.Timestamp.valueOf(start));
			System.out.println(preparedStatement);
			preparedStatement.setTimestamp(2, java.sql.Timestamp.valueOf(end));
			System.out.println(preparedStatement);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				selectedId.add(resultSet.getLong(1));
			}
		}
		return selectedId;
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
