package org.code.runs.ooo.ticketmaster.services;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;
import org.code.runs.ooo.common.models.Amount;
import org.code.runs.ooo.common.models.Currency;
import org.code.runs.ooo.common.models.PaymentMethod;
import org.code.runs.ooo.ticketmaster.models.Invoice;
import org.code.runs.ooo.ticketmaster.models.Seat;
import org.code.runs.ooo.ticketmaster.models.Show;
import org.code.runs.ooo.ticketmaster.models.Ticket;

public interface TicketService {
  Ticket bookTicket(Show show, Set<Seat> seats, PaymentMethod paymentMethod);

  Set<Seat> availableSeats(Show show);
}

class SimpleTicketService implements TicketService {
  private final ShowService showService;
  private final PaymentService paymentService;

  public SimpleTicketService(ShowService showService, PaymentService paymentService) {
    this.showService = showService;
    this.paymentService = paymentService;
  }

  @Override
  public Ticket bookTicket(Show show, Set<Seat> seats, PaymentMethod paymentMethod) {
    BigDecimal totalAmount = new BigDecimal(0);
    Currency currency = Currency.USD;
    for (Seat seat : seats) {
      if (!seat.isAvailable()) {
        throw new IllegalArgumentException("Seat is unavailable");
      }
      totalAmount = totalAmount.add(seat.price().value());
      currency = seat.price().currency();
    }

    Invoice invoice = paymentService.charge(Amount.newBuilder().setCurrency(currency).setValue(totalAmount).build(), paymentMethod);
    return Ticket.newBuilder()
        .setAuditorium(show.auditorium())
        .setSeats(seats)
        .setTheater(show.theater())
        .setTotalAmount(Amount.newBuilder().setValue(totalAmount).setCurrency(currency).build())
        .build();
  }

  @Override
  public Set<Seat> availableSeats(Show show) {
    return show.auditorium()
        .seats()
        .stream()
        .filter(Seat::isAvailable)
        .collect(Collectors.toSet());
  }
}