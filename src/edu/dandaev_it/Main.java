package edu.dandaev_it;

import edu.dandaev_it.dao.TicketDAO;

public class Main {

	public static void main(String[] args) {
		var selectedOptionalTickets = TicketDAO.getInstance().select(5L);
		selectedOptionalTickets.ifPresent(ticket -> {
			System.out.println(ticket);
		});
	}
}
