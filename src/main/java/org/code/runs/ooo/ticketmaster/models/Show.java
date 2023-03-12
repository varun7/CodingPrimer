package org.code.runs.ooo.ticketmaster.models;

import com.google.auto.value.AutoValue;
import java.time.LocalDateTime;
import java.time.Period;

@AutoValue
public abstract class Show {
  public abstract String showId();
  public abstract Theater theater();
  public abstract Auditorium auditorium();
  public abstract LocalDateTime showTime();
  public abstract Period duration();
  public abstract Movie movie();

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setShowId(String value);

    public abstract Builder setTheater(Theater value);

    public abstract Builder setAuditorium(Auditorium value);

    public abstract Builder setShowTime(LocalDateTime value);

    public abstract Builder setDuration(Period value);

    public abstract Builder setMovie(Movie value);

    public abstract Show build();
  }
}
