package org.code.runs.ooo.flight.services;

import org.code.runs.ooo.flight.models.Flight;
import org.code.runs.ooo.library.models.Ticket;
import java.util.Set;

public interface BookingService {

  Set<Ticket> bookTickets(String userId, Flight flight, int numberOfSeats);

}
