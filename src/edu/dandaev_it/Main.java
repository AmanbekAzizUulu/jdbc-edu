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

		String delete_referenced_ticket = """
				delete
				from
					ticket
				where
					flight_id = ?
				""";

		String select_all_from_flight_table = """
				select
					id
				from
					flight
				""";

		try (Connection connection = ConnectionManager.open();
				PreparedStatement preparedStatement_1 = connection.prepareStatement(delete_flight_by_id);
				PreparedStatement preparedStatement_2 = connection.prepareStatement(delete_referenced_ticket);
				PreparedStatement preparedStatement_3 = connection.prepareStatement(select_all_from_flight_table)) {

			// use debugger to better understanding what is happening here

			// что бы избежать SQLIntegrityConstraintViolationException, сначала удалим
			// все строки из таблицы ticket которые ссылаются на id из таблицы flight
			preparedStatement_2.setLong(1, flight_id);
			preparedStatement_2.executeUpdate();


			// моделирование возникновения ошибки при выполнении запросов
			// без применения механизма транзакций
			if (true) {
				throw new RuntimeException("");
			}

			// после удаления всех строк из таблицы ticket, которые ссылются на строки
			// с соответствующим id из таблицы flight, удаляться строки из таблицы flight
			preparedStatement_1.setLong(1, flight_id);
			preparedStatement_1.executeUpdate();

			ResultSet resultSet = preparedStatement_3.executeQuery();
			while (resultSet.next()) {
				System.out.println("id = " + resultSet.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
