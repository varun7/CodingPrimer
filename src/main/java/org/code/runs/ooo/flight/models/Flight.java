package org.code.runs.ooo.flight.models;

import com.google.auto.value.AutoValue;
import java.time.LocalDate;
import java.time.Period;
import java.util.Set;

@AutoValue
public abstract class Flight {
  public abstract String flightId();
  public abstract String flightNumber();
  public abstract String airlines();
  public abstract Set<Seat> seats();
  public abstract String from();
  public abstract String to();
  public abstract LocalDate date();
  public abstract Period duration();

  public static Builder newBuilder() {
    return new AutoValue_Flight.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setFlightId(String value);

    public abstract Builder setFlightNumber(String value);

    public abstract Builder setAirlines(String value);

    public abstract Builder setSeats(Set<Seat> value);

    public abstract Builder setFrom(String value);

    public abstract Builder setTo(String value);

    public abstract Builder setDate(LocalDate value);

    public abstract Builder setDuration(Period value);

    public abstract Flight build();
  }
}
