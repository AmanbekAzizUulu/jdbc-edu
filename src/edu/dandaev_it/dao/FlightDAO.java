package edu.dandaev_it.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import edu.dandaev_it.entity.Flight;
import edu.dandaev_it.entity.Ticket;
import edu.dandaev_it.exceptions.DAOException;
import edu.dandaev_it.util.ConnectionManager;

public class FlightDAO implements DAO<Long, Flight> {
	private static final FlightDAO INSTANCE = new FlightDAO();

	public static FlightDAO getInstance() {
		return INSTANCE;
	}

	private FlightDAO() {
	}

	private static final String SELECT_BY_ID = """
			select
				id,
				flight_no,
				departure_date,
				departure_airport_code,
				arrival_date,
				arrival_airport_code,
				aircraft_id,
				status
			from
				flight
			where
				id = ? ;
			""";

	@Override
	public List<Flight> selectAll() {
		throw new UnsupportedOperationException("Unimplemented method 'selectAll'");
	}

	@Override
	public Optional<Flight> select(Long flightId) {
		try (var connection = ConnectionManager.get()) {
			return select(flightId, connection);
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public Optional<Flight> select(Long flightId, Connection connection) {
		try (var preparedStatement = connection.prepareStatement(SELECT_BY_ID);) {
			preparedStatement.setLong(1, flightId);
			var selectedFlight = preparedStatement.executeQuery();
			Flight flight = null;
			if (selectedFlight.next()) {
				flight = buildFlightEntity(selectedFlight);
			}
			return Optional.ofNullable(flight);
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	@Override
	public void update(Flight ticket) {
		throw new UnsupportedOperationException("Unimplemented method 'update'");
	}

	@Override
	public Flight insert(Flight ticket) {
		throw new UnsupportedOperationException("Unimplemented method 'insert'");
	}

	@Override
	public boolean delete(Long ticketId) {
		throw new UnsupportedOperationException("Unimplemented method 'delete'");
	}

	private Flight buildFlightEntity(ResultSet resultSet) throws SQLException {
		return new Flight(
				resultSet.getLong(1),
				resultSet.getString(2),
				resultSet.getTimestamp(3).toLocalDateTime(),
				resultSet.getString(4),
				resultSet.getTimestamp(5).toLocalDateTime(),
				resultSet.getString(6),
				resultSet.getInt(7),
				resultSet.getString(8));
	}

}
