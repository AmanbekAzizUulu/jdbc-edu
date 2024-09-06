package edu.dandaev_it;

import java.sql.SQLException;

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
