package edu.dandaev_it;

import edu.dandaev_it.dao.TicketDAO;
import edu.dandaev_it.dto.TicketFilter;

public class Main {

	public static void main(String[] args) {
		var ticketDAO = TicketDAO.getInstance();

		// new ticket insertion
		// var ticket = new Ticket("PQ17ET", "Eleonora Nazarova", 9L, "C4",
		// BigDecimal.valueOf(145.00));
		// var insertedTicket = ticketDAO.insert(ticket);

		// ticket deletion
		// Long ticketId = 160L;
		// var isDeleted = ticketDAO.delete(ticketId);
		// out.println("Ticket by id = " + ticketId + " was deleted: " + isDeleted);

		// selection by id
		// var selectedOptionalTicket = ticketDAO.select(2L);
		// selectedOptionalTicket.ifPresent(ticket -> {
		// 	out.println("Selected ticket:\n" + ticket);
		// 	out.println();
		// });

		// update value of cost column
		// selectedOptionalTicket.ifPresent(ticket -> {
		// 	ticket.setCost(BigDecimal.valueOf(200.00));
		// 	ticketDAO.update(ticket);
		// });

		// selectedOptionalTicket = ticketDAO.select(2L);
		// selectedOptionalTicket.ifPresent(ticket -> {
		// 	out.println("Selected ticket:\n" + ticket);
		// 	out.println();
		// });

		// select all from ticket table
		// List<Ticket> tickets = ticketDAO.selectAll();
		// tickets.forEach(System.out::println);

		// selection with filtering
		// var ticketFilter = new TicketFilter(3, 2);
		// var tickets = TicketDAO.getInstance().selectAll(ticketFilter);
		// tickets.forEach(System.out::println);

		var ticketFilter = new TicketFilter(3, 2, null, "A1");
		var tickets = TicketDAO.getInstance().selectAll(ticketFilter);
		tickets.forEach(System.out::println);
	}
}
