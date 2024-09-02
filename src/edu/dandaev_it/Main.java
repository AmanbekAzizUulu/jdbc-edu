package edu.dandaev_it;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import edu.dandaev_it.util.ConnectionManager;

public class Main {

	public static void main(String[] args) throws SQLException {
		String str_1 = """
				INSERT
					INTO flight (id, flight_no, departure_date, departure_airport_code, arrival_date, arrival_airport_code, aircraft_id, status)
				VALUES
					('CV9827', '2021-02-05 06:00:00', 'MNK', '2021-02-05 07:15:00', 'MSK', 4, 'SCHEDULED'),
					('QS8712', '2021-02-18 09:45:00', 'MNK', '2021-02-18 12:56:00', 'LDN', 2, 'SCHEDULED');
								""";
		String insert_into_flight = """
				insert
				into
					flight
						(
							flight_no,
							departure_date,
							departure_airport_code,
							arrival_date,
							arrival_airport_code,
							aircraft_id,
							status
						)
				values
					(
						?,
						?,
						?,
						?,
						?,
						?,
						?
					);
				""";
		String insert_into_ticket_1 = """
				insert
				into
					ticket (passenger_no, passenger_name, flight_id, seat_no, cost)
				values
					('ZXCVBN', 'Денис Кривцов', 5, 'B2', 185.00);
								""";
		String insert_into_ticket_2 = """
				insert
				into
					ticket (passenger_no, passenger_name, flight_id, seat_no, cost)
				values
					('HJKLPO', 'Евгения Воронова', 8, 'D2', 115.00);
								""";

		Connection connection = null;
		PreparedStatement batch_statement_to_insert_into_flight = null;
		Statement batch_statement_to_insert_into_ticket = null;
		
		try {
			connection = ConnectionManager.open();
			connection.setAutoCommit(false);

			batch_statement_to_insert_into_flight = connection.prepareStatement(insert_into_flight);

			batch_statement_to_insert_into_flight.setString(1, "MN3002");
			batch_statement_to_insert_into_flight.setTimestamp(2, Timestamp.valueOf("2020-12-20 15:45:00"));
			batch_statement_to_insert_into_flight.setString(3, "MNK");
			batch_statement_to_insert_into_flight.setTimestamp(4, Timestamp.valueOf("2020-12-20 18:30:00"));
			batch_statement_to_insert_into_flight.setString(5, "LDN");
			batch_statement_to_insert_into_flight.setLong(6, 1L);
			batch_statement_to_insert_into_flight.setString(7, "ARRIVED");
			batch_statement_to_insert_into_flight.addBatch();

			batch_statement_to_insert_into_flight.setString(1, "BC2801");
			batch_statement_to_insert_into_flight.setTimestamp(2, Timestamp.valueOf("2021-01-02 22:15:00"));
			batch_statement_to_insert_into_flight.setString(3, "LDN");
			batch_statement_to_insert_into_flight.setTimestamp(4, Timestamp.valueOf("2021-01-03 01:30:00"));
			batch_statement_to_insert_into_flight.setString(5, "MNK");
			batch_statement_to_insert_into_flight.setLong(6, 2L);
			batch_statement_to_insert_into_flight.setString(7, "ARRIVED");
			batch_statement_to_insert_into_flight.addBatch();

			batch_statement_to_insert_into_flight.setString(1, "TR3103");
			batch_statement_to_insert_into_flight.setTimestamp(2, Timestamp.valueOf("2021-01-15 14:25:00"));
			batch_statement_to_insert_into_flight.setString(3, "MSK");
			batch_statement_to_insert_into_flight.setTimestamp(4, Timestamp.valueOf("2021-01-15 19:50:00"));
			batch_statement_to_insert_into_flight.setString(5, "BSL");
			batch_statement_to_insert_into_flight.setLong(6, 3L);
			batch_statement_to_insert_into_flight.setString(7, "ARRIVED");
			batch_statement_to_insert_into_flight.addBatch();

			int[] affected_rows_1 = batch_statement_to_insert_into_flight.executeBatch();
			System.out.println("Batch processing completed. Rows affected: " + affected_rows_1.length);

			batch_statement_to_insert_into_ticket = connection.createStatement();

			batch_statement_to_insert_into_ticket.addBatch(insert_into_ticket_1);
			batch_statement_to_insert_into_ticket.addBatch(insert_into_ticket_2);

			int[] affected_rows_2 = batch_statement_to_insert_into_ticket.executeBatch();
			System.out.println("Batch processing completed. Rows affected: " + affected_rows_2.length);

			connection.commit();


		} catch (Exception e) {
			if (connection != null) {
				connection.rollback();
			}
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
		}

	}

}
