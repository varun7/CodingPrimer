package org.code.runs.ooo.library.models;

import com.google.common.base.Preconditions;
import java.util.Optional;

public final class Query {
  private final Optional<String> title;
  private final Optional<String> author;

  private Query(String title, String author) {
    Preconditions.checkArgument(title != null || author != null, "Atleast one of author or title must be passed for search query");
    this.title = Optional.ofNullable(title);
    this.author = Optional.ofNullable(author);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public Optional<String> getTitle() {
    return title;
  }

  public Optional<String> getAuthor() {
    return author;
  }

  public static class Builder {
    private String title;
    private String author;

    public Builder setTitle(String title) {
      this.title = title;
      return this;
    }

    public Builder setAuthor(String author) {
      this.author = author;
      return this;
    }

    public Query build() {
      return new Query(title, author);
    }
  }
}
