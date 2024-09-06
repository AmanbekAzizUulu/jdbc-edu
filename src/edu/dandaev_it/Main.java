package edu.dandaev_it;

import java.math.BigDecimal;

import edu.dandaev_it.dao.TicketDAO;
import edu.dandaev_it.entity.Ticket;

import static java.lang.System.out;

public class Main {

	public static void main(String[] args) {
		var ticketDAO = TicketDAO.getInstance();
		
		// var ticket = new Ticket("PQ17ET", "Eleonora Nazarova", 9L, "C4", BigDecimal.valueOf(145.00));
		// var insertedTicket = ticketDAO.insert(ticket);

		Long ticketId = 160L;
		var isDeleted = ticketDAO.delete(ticketId);
		out.println("Ticket by id = " + ticketId + " was deleted: " + isDeleted);
	}
}
