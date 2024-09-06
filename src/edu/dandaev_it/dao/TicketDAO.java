package edu.dandaev_it.dao;

import static java.util.stream.Collectors.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import edu.dandaev_it.dto.TicketFilter;
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
	private static final String UPDATE_ROW_IN_TICKET_TABLE = """
			update
				ticket
			set
				passenger_no = ? ,
				passenger_name = ? ,
				flight_id = ? ,
				seat_no = ? ,
				cost = ?
			where
				id = ? ;
			""";

	private static final String SELECT_BY_ID = """
			select
				id,
				passenger_no,
				passenger_name,
				flight_id,
				seat_no,
				cost
			from
				ticket
			where
				id = ? ;
			""";
	private static final String SELECT_ALL = """
			select
				id,
				passenger_no,
				passenger_name,
				flight_id,
				seat_no,
				cost
			from
				ticket
			""";
	private static final String SELECT_ALL_WITH_FILTER = """
			select
			    id,
			    passenger_no,
			    passenger_name,
			    flight_id,
			    seat_no,
			    cost
			from
			    ticket
			limit ? offset ? ;
			""";

	private TicketDAO() {
	}

	public static TicketDAO getInstance() {
		return INSTANCE;
	}

	public List<Ticket> selectAll(TicketFilter filter) {
		List<Object> parameters = new ArrayList<Object>();
		List<String> filterConditions = new ArrayList<String>();

		if (filter.seatNo() != null) {
			filterConditions.add("seat_no like ?");
			parameters.add("%" + filter.seatNo() + "%");
		}

		if (filter.passengerFullName() != null) {
			filterConditions.add("passenger_name = ?");
			parameters.add(filter.passengerFullName());
		}

		// Формируем часть WHERE только если есть фильтры
		// String _where = filterConditions.isEmpty() ? "" : " where " + String.join(" and ", filterConditions);

		// Полный SQL запрос с LIMIT и OFFSET
		// String select_all_with_condition = SELECT_ALL + _where + " limit ? offset ?";

		parameters.add(filter.limit());
		parameters.add(filter.offset());

		var _where = filterConditions.stream()
				.collect(joining(" and ", " where ", " limit ? offset ? "));
		String select_all_with_condition = SELECT_ALL + _where;

		try (var connection = ConnectionManager.get();
				var preparedStatement = connection.prepareStatement(select_all_with_condition)) {

			for (int i = 0; i < parameters.size(); i++) {
				preparedStatement.setObject(i + 1, parameters.get(i));
			}

			System.out.println(preparedStatement);
			var selectedRows = preparedStatement.executeQuery();
			List<Ticket> tickets = new ArrayList<Ticket>();
			while (selectedRows.next()) {
				tickets.add(new Ticket(selectedRows.getLong(1),
						selectedRows.getString(2),
						selectedRows.getString(3),
						selectedRows.getLong(4),
						selectedRows.getString(5),
						selectedRows.getBigDecimal(6)));
			}

			return tickets;
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<Ticket> selectAll() {
		try (var connection = ConnectionManager.get();
				var preparedStatement = connection.prepareStatement(SELECT_ALL)) {

			var tickets = new ArrayList<Ticket>();
			var selectedTickets = preparedStatement.executeQuery();

			while (selectedTickets.next()) {
				var ticketHolder = new Ticket();

				ticketHolder.setTicketId(selectedTickets.getLong(1));
				ticketHolder.setPassengerNo(selectedTickets.getString(2));
				ticketHolder.setPassengerFullName(selectedTickets.getString(3));
				ticketHolder.setFlightId(selectedTickets.getLong(4));
				ticketHolder.setSeatNo(selectedTickets.getString(5));
				ticketHolder.setCost(selectedTickets.getBigDecimal(6));

				tickets.add(ticketHolder);
			}
			return tickets;
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public Optional<Ticket> select(Long ticketID) {
		try (var connection = ConnectionManager.get();
				var preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
			preparedStatement.setLong(1, ticketID);

			var selectedTicket = preparedStatement.executeQuery();
			Ticket ticket = null;
			if (selectedTicket.next()) {
				ticket = new Ticket(selectedTicket.getLong(1),
						selectedTicket.getString(2),
						selectedTicket.getString(3),
						selectedTicket.getLong(4),
						selectedTicket.getString(5),
						selectedTicket.getBigDecimal(6));
			}
			return Optional.ofNullable(ticket);
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void update(Ticket ticket) {
		try (var connection = ConnectionManager.get();
				var preparedStatement = connection.prepareStatement(UPDATE_ROW_IN_TICKET_TABLE)) {

			preparedStatement.setString(1, ticket.getPassengerNo());
			preparedStatement.setString(2, ticket.getPassengerFullName());
			preparedStatement.setLong(3, ticket.getFlightId());
			preparedStatement.setString(4, ticket.getSeatNo());
			preparedStatement.setBigDecimal(5, ticket.getCost());
			preparedStatement.setLong(6, ticket.getTicketId());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public Ticket insert(Ticket ticket) {
		try (Connection connection = ConnectionManager.get();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_TICKET_TABLE,
						Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, ticket.getPassengerNo());
			preparedStatement.setString(2, ticket.getPassengerFullName());
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
