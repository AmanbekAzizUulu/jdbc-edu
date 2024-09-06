package edu.dandaev_it.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import edu.dandaev_it.entity.Ticket;
import edu.dandaev_it.exceptions.DAOException;
import edu.dandaev_it.util.ConnectionManager;

public class TicketDAO {
	private static final TicketDAO INSTANCE = new TicketDAO();

	private static final String DELETE_TICKET_BY_ID = """
			delete
			from
				ticket
			where
				id = ? ;
			""";
	private static final String INSERT_INTO_TICKET_TABLE = """
			insert
			into
				ticket
					(
						passenger_no,
						passenger_name,
						flight_id,
						seat_no,
						cost
					)
			values
				(
					?,
					?,
					?,
					?,
					?
				);
			""";

	private TicketDAO() {
	}

	public static TicketDAO getInstance() {
		return INSTANCE;
	}

	public Ticket insert(Ticket ticket) {
		try (Connection connection = ConnectionManager.get();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_TICKET_TABLE,
						Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, ticket.getPassengerNo());
			preparedStatement.setString(2, ticket.getPassengerFirstName());
			preparedStatement.setLong(3, ticket.getFlightId());
			preparedStatement.setString(4, ticket.getSeatNo());
			preparedStatement.setBigDecimal(5, ticket.getCost());

			preparedStatement.executeUpdate();

			var generatedKeys = preparedStatement.getGeneratedKeys();

			if (generatedKeys.next()) {
				ticket.setTicketId(generatedKeys.getLong(1));
			}

			return ticket;
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public boolean delete(Long ticketId) {
		try (Connection connection = ConnectionManager.get();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TICKET_BY_ID)) {
			preparedStatement.setLong(1, ticketId);
			return preparedStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
}
