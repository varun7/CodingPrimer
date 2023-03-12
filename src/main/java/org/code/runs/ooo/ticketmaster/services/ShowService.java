package org.code.runs.ooo.ticketmaster.services;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.code.runs.ooo.ticketmaster.models.Show;

public interface ShowService {

  void createShow(Show show);

  void cancelShow(Show show);

  List<Show> search(String city, String movieTitle, LocalDate date);
}

class SimpleShowService implements ShowService {
  // City, MovieTitle, Show
  private final Table<String, String, Table<String, LocalDate, Show>> shows;

  public SimpleShowService() {
    shows = HashBasedTable.create();
  }

  @Override
  public void createShow(Show show) {
    String city = show.theater().address().city();
    String movieTitle = show.movie().title();
    if (!shows.contains(city, movieTitle)) {
      shows.put(city, movieTitle, HashBasedTable.create());
    }
    Table<String, LocalDate, Show> movieShowsInCity = shows.get(city, movieTitle);
    movieShowsInCity.put(show.showId(), show.showTime().toLocalDate(), show);
  }

  @Override
  public void cancelShow(Show show) {
    String city = show.theater().address().city();
    String movieTitle = show.movie().title();
    Table<String, LocalDate, Show> movieShowsInCity = shows.get(city, movieTitle);
    movieShowsInCity.remove(show.showId(), show.showTime().toLocalDate());
  }

  public void updateShow(Show show) {
    String city = show.theater().address().city();
    String movieTitle = show.movie().title();
    if (!shows.contains(city, movieTitle)) {
      throw new IllegalArgumentException("Cannot find the show");
    }
    Table<String, LocalDate, Show> movieShowsInCity = shows.get(city, movieTitle);
    movieShowsInCity.put(show.showId(), show.showTime().toLocalDate(), show);
  }

  @Override
  public List<Show> search(String city, String movieTitle, LocalDate date) {
    Table<String, LocalDate, Show> movieShowsInCity = shows.get(city, movieTitle);
    return new ArrayList<>(movieShowsInCity.column(date).values());
  }
}