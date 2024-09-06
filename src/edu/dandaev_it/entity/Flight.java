package edu.dandaev_it.entity;

import java.time.LocalDateTime;

public record Flight(
		Long flightId,
		String flightNo,
		LocalDateTime departureDate,
		String departureAirportCode,
		LocalDateTime arrivalDate,
		String arrivalAirportCode,
		Integer aircraftId,
		String status) {

	@Override
	public final String toString() {
		return "Flight {" +
				"\n\t\tflight_id = " + flightId +
				"\n\t\tflight_no = " + flightNo +
				"\n\t\tdeparture_date = " + departureDate +
				"\n\t\tdeparture_airport_code = " + departureAirportCode +
				"\n\t\tarrival_date = " + arrivalDate +
				"\n\t\tarrival_airport_code = " + arrivalAirportCode +
				"\n\t\taircraft_id = " + aircraftId +
				"\n\t\tstatus = " + status +
				"\n\t}";
	}
}
