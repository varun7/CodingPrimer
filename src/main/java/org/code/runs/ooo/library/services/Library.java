package org.code.runs.ooo.library.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.code.runs.ooo.library.models.Book;
import org.code.runs.ooo.library.models.Invoice;
import org.code.runs.ooo.library.models.Member;
import org.code.runs.ooo.library.models.Ticket;

public class Library {
  private final CatalogueService catalogueService;
  private final RentService rentService;
  private final MembershipService membershipService;

  public Library() {
    this.catalogueService = new DefaultCatalogueService();
    this.rentService = new SimpleRentingService(catalogueService, new SimplePaymentService());
    this.membershipService = new SimpleMembershipService();
  }

  public static void main(String[] args) {
    Library library = new Library();
    Member m1 = new Member("Varun", "Bangalore", "1111111111", "runs@google.com", "m1");
    Book b1 = new Book("1", "Gone with the wind", "Robert Junior", "Macmillan", LocalDate.now().minusDays(100));
    Book b2 = new Book("2", "Gone with the wind", "Robert Junior", "Macmillan", LocalDate.now().minusDays(100));
    Book b3 = new Book("3", "Gone with the wind", "Robert Junior", "Macmillan", LocalDate.now().minusDays(100));
    Book b4 = new Book("4", "Swami Vivekananda", "Swami Vivekananda", "Rama Krishna", LocalDate.now().minusDays(200));
    Book b5 = new Book("5", "Day and night", "someone", "don't know", LocalDate.now().minusDays(200));

    library.addMember(m1);
    library.addBook(b1);
    library.addBook(b2);
    library.addBook(b3);
    library.addBook(b4);
    library.addBook(b5);

    System.out.println("\n\n");
    library.search("Gone with the wind").forEach(System.out::println);
    Ticket ticket1 = library.rentBook(m1, b1);
    Ticket ticket2 = library.rentBook(m1, b2);
    System.out.println("\n\n");
    library.search("Gone with the wind").forEach(System.out::println);
    System.out.println("\n\n");
    library.returnBook(ticket2);
    library.search("Gone with the wind").forEach(System.out::println);
  }

  public void addMember(Member member) {
    membershipService.addMember(member);
  }

  public void removeMember(Member member) {
    membershipService.removeMember(member);
  }

  public Optional<Invoice> returnBook(Ticket ticket) {
    return rentService.returnBook(ticket);
  }

  public void addBook(Book book) {
    catalogueService.addBook(book);
  }

  public Ticket rentBook(Member member, Book book) {
    return rentService.rent(member, book);
  }

  public List<Book> search(String title) {
    return catalogueService.search(title);
  }
}
