package org.code.runs.ooo.library.services;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import java.util.ArrayList;
import java.util.List;
import org.code.runs.ooo.library.models.Book;

public interface CatalogueService {
  void addBook(Book book);

  void removeBook(Book book);

  void updateBook(Book book);

  List<Book> search(String title);
}

class DefaultCatalogueService implements CatalogueService {

  Table<String, String, Book> catalogueTable;

  public DefaultCatalogueService() {
    catalogueTable = HashBasedTable.create();
  }

  @Override
  public void addBook(Book book) {
    catalogueTable.put(book.getBookId(), book.getTitle(), book);
  }

  @Override
  public void removeBook(Book book) {
    catalogueTable.remove(book.getBookId(), book.getTitle());
  }

  @Override
  public void updateBook(Book book) {
    catalogueTable.put(book.getBookId(), book.getTitle(), book);
  }

  @Override
  public List<Book> search(String title) {
    return new ArrayList<>(catalogueTable.column(title).values());
  }
}