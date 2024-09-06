package edu.dandaev_it.dto;

public record TicketFilter(
							int limit,
							int offset,
							String passengerFullName,
							String seatNo
						   ) {

}
