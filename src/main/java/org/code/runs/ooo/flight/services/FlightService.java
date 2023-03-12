package org.code.runs.ooo.flight.services;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.code.runs.ooo.flight.models.Flight;
import org.code.runs.ooo.flight.models.SeatType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public interface FlightService {

  void addFlight(Flight flight);

   List<Flight> search(String from, String to, LocalDate departureDate);

   long availableSeats(String flightId, SeatType type);
}

class SimpleFlightService implements FlightService {
  // FlightId to FlightMap.
  private final Map<String , Flight> flightMap;

  // From, To, -> Map<LocalDate, FlightId>
  private final Table<String, String, Map<LocalDate, Set<String>>> flightTable;

  public SimpleFlightService() {
    flightMap = new HashMap<>();
    flightTable = HashBasedTable.create();
  }

  @Override
  public void addFlight(Flight flight) {
    if (flightMap.containsKey(flight.flightId())) {
      throw new IllegalArgumentException("Flight already exist in the system");
    }
    flightMap.put(flight.flightId(), flight);
    addFlightToTable(flight);
  }

  @Override
  public List<Flight> search(String from, String to, LocalDate departureDate) {
    Map<LocalDate, Set<String>> allFlights = flightTable.get(from, to);
    if (!allFlights.containsKey(departureDate)) {
      return new ArrayList<>();
    }
    return allFlights.get(departureDate)
        .stream()
        .map(flightMap::get)
        .collect(Collectors.toList());
  }

  @Override
  public long availableSeats(String flightId, SeatType type) {
    if (!flightMap.containsKey(flightId)) {
      throw new IllegalArgumentException("Flight not present in system");
    }
    return flightMap.get(flightId)
        .seats()
        .stream()
        .filter(s -> s.isAvaialble() && s.seatType() == type)
        .count();
  }

  private void addFlightToTable(Flight flight) {
    if (!flightTable.contains(flight.from(), flight.to())) {
      Map<LocalDate, Set<String>> departureDateMap = new HashMap<>();
      departureDateMap.put(flight.date(), new HashSet<>());
      flightTable.put(flight.from(), flight.to(), departureDateMap);
    }
    Set<String> flights = flightTable.get(flight.from(), flight.to()).get(flight.date());
    flights.add(flight.flightId());
  }
}