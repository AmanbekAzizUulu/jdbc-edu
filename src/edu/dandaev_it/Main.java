package edu.dandaev_it;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.dandaev_it.util.ConnectionManager;
import edu.dandaev_it.util.DatabaseMetadataManager;

public class Main {

	public static void main(String[] args){
		try {
			DatabaseMetadataManager.checkMetaDate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


}
