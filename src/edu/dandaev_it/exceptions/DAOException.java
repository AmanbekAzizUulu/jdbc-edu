package edu.dandaev_it.exceptions;

public class DAOException extends RuntimeException {
	public DAOException(Throwable exc) {
		super("Exception when operating with Data Access Object", exc);
	}
}
