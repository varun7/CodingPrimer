package org.code.runs.ooo.library.models;

import java.time.LocalDate;

public class Book {
  private final String bookId;
  private final String title;
  private final String author;
  private final String publisher;
  private final LocalDate publishingDate;
  private boolean isRented;

  public Book(String bookId, String title, String author, String publisher,
      LocalDate publishingDate) {
    this.bookId = bookId;
    this.title = title;
    this.author = author;
    this.publisher = publisher;
    this.publishingDate = publishingDate;
  }

  public String getBookId() {
    return bookId;
  }

  public String getTitle() {
    return title;
  }

  public String getAuthor() {
    return author;
  }

  public String getPublisher() {
    return publisher;
  }

  public LocalDate getPublishingDate() {
    return publishingDate;
  }

  public boolean isRented() {
    return isRented;
  }

  public void markRented() {
    this.isRented = true;
  }

  public void markNotRented() {
    this.isRented = false;
  }

  @Override
  public String toString() {
    return "Book{" +
        "bookId='" + bookId + '\'' +
        ", title='" + title + '\'' +
        ", author='" + author + '\'' +
        ", publisher='" + publisher + '\'' +
        ", publishingDate=" + publishingDate +
        ", isRented=" + isRented +
        '}';
  }
}
