package edu.dandaev_it.entity;

import java.math.BigDecimal;

public class Ticket {
	private Long ticketId;
	private String passengerNo;
	private String passengerFullName;
	private Long flightId;
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

	public Long getFlightId() {
		return flightId;
	}

	public void setFlightId(Long flightId) {
		this.flightId = flightId;
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

	public Ticket(Long ticketId, String passengerNo, String passengerFullName, Long flightId, String seatNo,
			BigDecimal cost) {
		this.ticketId = ticketId;
		this.passengerNo = passengerNo;
		this.passengerFullName = passengerFullName;
		this.flightId = flightId;
		this.seatNo = seatNo;
		this.cost = cost;
	}

	public Ticket(String passengerNo, String passengerFullName, Long flightId, String seatNo, BigDecimal cost) {
		this.passengerNo = passengerNo;
		this.passengerFullName = passengerFullName;
		this.flightId = flightId;
		this.seatNo = seatNo;
		this.cost = cost;
	}

	public Ticket() {
	}

	@Override
	public String toString() {
		return "Ticket {" +
				"\n\tticketId: " + ticketId +
				"\n\tpassengerNo: " + passengerNo +
				"\n\tpassengerFullName: " + passengerFullName +
				"\n\tflightId: " + flightId +
				"\n\tseatNo: " + seatNo +
				"\n\tcost: " + cost +
				"\n}";
	}

}
