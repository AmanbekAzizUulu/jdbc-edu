package edu.dandaev_it.entity;

import java.math.BigDecimal;

public class Ticket {
	private Long ticketId;
	private String passengerNo;
	private String passengerFullName;
	private Flight flight;
	private String seatNo;
	private BigDecimal cost;

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public String getPassengerNo() {
		return passengerNo;
	}

	public void setPassengerNo(String passengerNo) {
		this.passengerNo = passengerNo;
	}

	public String getPassengerFullName() {
		return passengerFullName;
	}

	public void setPassengerFullName(String passengerFullName) {
		this.passengerFullName = passengerFullName;
	}

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public String getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public Ticket(Long ticketId, String passengerNo, String passengerFullName, Flight flight, String seatNo,
			BigDecimal cost) {
		this.ticketId = ticketId;
		this.passengerNo = passengerNo;
		this.passengerFullName = passengerFullName;
		this.flight = flight;
		this.seatNo = seatNo;
		this.cost = cost;
	}

	public Ticket(String passengerNo, String passengerFullName, Flight flight, String seatNo, BigDecimal cost) {
		this.passengerNo = passengerNo;
		this.passengerFullName = passengerFullName;
		this.flight = flight;
		this.seatNo = seatNo;
		this.cost = cost;
	}

	public Ticket() {
	}

	@Override
	public String toString() {
		return "Ticket {" +
				"\n\tticket_id: " + ticketId +
				"\n\tpassenger_no: " + passengerNo +
				"\n\tpassenger_full_name: " + passengerFullName +
				"\n\t" + flight +
				"\n\tseat_no: " + seatNo +
				"\n\tcost: " + cost +
				"\n}";
	}

}
