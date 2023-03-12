package org.code.runs.ooo.library.models;

import java.time.LocalDate;

public class Ticket {
  private final String ticketId;
  private final Book rentedBook;
  private final Member member;
  private final LocalDate returnBeforeDate;

  public Ticket(String ticketId, Book rentedBook, Member member, LocalDate returnBeforeDate) {
    this.ticketId = ticketId;
    this.rentedBook = rentedBook;
    this.member = member;
    this.returnBeforeDate = returnBeforeDate;
  }

  public String getTicketId() {
    return ticketId;
  }

  public Book getRentedBook() {
    return rentedBook;
  }

  public Member getMember() {
    return member;
  }

  public LocalDate getReturnBeforeDate() {
    return returnBeforeDate;
  }

  @Override
  public String toString() {
    return "Ticket{" +
        "ticketId='" + ticketId + '\'' +
        ", rentedBook=" + rentedBook +
        ", member=" + member +
        ", returnBeforeDate=" + returnBeforeDate +
        '}';
  }
}
