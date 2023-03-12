package org.code.runs.ooo.flight.services;

import org.code.runs.ooo.flight.models.Flight;
import org.code.runs.ooo.flight.models.SeatType;
import java.time.LocalDate;
import java.util.List;

public class FlightManagementSystem {

  private final FlightService flightService;

  public FlightManagementSystem() {
    this.flightService = new SimpleFlightService();
  }

  public List<Flight> searchFlights(String from, String to, LocalDate departureDate) {
    return flightService.search(from, to, departureDate);
  }

  public void addFlight(Flight flight) {
    flightService.addFlight(flight);
  }

  public long availableSeats(String flightId, SeatType type) {
    return flightService.availableSeats(flightId, type);
  }
}
