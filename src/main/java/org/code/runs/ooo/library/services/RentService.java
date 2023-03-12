package org.code.runs.ooo.library.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;
import java.util.UUID;
import org.code.runs.ooo.library.models.Amount;
import org.code.runs.ooo.library.models.Book;
import org.code.runs.ooo.library.models.Currency;
import org.code.runs.ooo.library.models.Invoice;
import org.code.runs.ooo.library.models.Member;
import org.code.runs.ooo.library.models.Ticket;

public interface RentService {

  Ticket rent(Member member, Book book);

  Optional<Invoice> returnBook(Ticket ticket);
}

class SimpleRentingService implements RentService {
  private static final int LATE_PAYMENT_FEE = 5;
  private final CatalogueService catalogue;
  private final PaymentService paymentService;

  public SimpleRentingService(CatalogueService catalogue, PaymentService paymentService) {
    this.catalogue = catalogue;
    this.paymentService = paymentService;
  }

  @Override
  public Ticket rent(Member member, Book book) {
    if (book.isRented()) {
      throw new IllegalArgumentException("Book is unavailable for renting");
    }
    book.markRented();
    catalogue.updateBook(book);
    return new Ticket(UUID.randomUUID().toString(), book, member, LocalDate.now().plusDays(5));
  }

  @Override
  public Optional<Invoice> returnBook(Ticket ticket) {
    LocalDate toReturnDate = ticket.getReturnBeforeDate();
    Book book = ticket.getRentedBook();
    book.markNotRented();
    catalogue.updateBook(book);

    if (LocalDate.now().isBefore(toReturnDate)) {
      Period period = Period.between(LocalDate.now(), toReturnDate);
      Invoice invoice = paymentService.charge(ticket.getMember(), new Amount(Currency.USD, BigDecimal.valueOf(period.getDays() * LATE_PAYMENT_FEE)));
      return Optional.of(invoice);
    }
    return Optional.empty();
  }
}