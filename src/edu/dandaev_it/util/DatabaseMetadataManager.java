package edu.dandaev_it.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class DatabaseMetadataManager {

	private DatabaseMetadataManager() {
	}

	public static void checkMetaData() throws SQLException {
		try (Connection connection = ConnectionManager.get()) {

			DatabaseMetaData databaseMetaData = connection.getMetaData();

			System.out.println("Database Product Name: " + databaseMetaData.getDatabaseProductName());
			System.out.println("Database Product Version: " + databaseMetaData.getDatabaseProductVersion());
			System.out.println("Driver Name: " + databaseMetaData.getDriverName());
			System.out.println("Driver Version: " + databaseMetaData.getDriverVersion());

			// Получаем список таблиц в базе данных
			ResultSet tables = databaseMetaData.getTables(null, null, "%", new String[] { "TABLE" });
			while (tables.next()) {
				System.out.println("Table Name: " + tables.getString("TABLE_NAME"));
			}

			// Про катлоги и схемы читай в файле README.md
			ResultSet catalogs = databaseMetaData.getCatalogs();
			while (catalogs.next()) {
				System.out.println("Catalog name: " + catalogs.getString("TABLE_CAT"));
			}

			// this will not work properly in mysql dbms
			ResultSet schemas = databaseMetaData.getSchemas();
			while (schemas.next()) {
				System.out.println("Schema name: " + schemas.getString("TABLE_CATALOG"));
			}

			ResultSet columns = databaseMetaData.getColumns(null, null, "%", null);
			while (columns.next()) {
				System.out.println("Column name: " + columns.getString("COLUMN_NAME") + "\t\t\t\t\t| type = "
						+ columns.getString("TYPE_NAME"));
			}

			columns.close();
			catalogs.close();
			tables.close();
			schemas.close();
		}
	}
}
