package org.code.runs.ooo.ticketmaster.models;

import com.google.auto.value.AutoValue;
import org.code.runs.ooo.common.models.Amount;

@AutoValue
public abstract class Seat {
  public abstract String seatNumber();
  public abstract boolean isAvailable();
  public abstract SeatType seatType();
  public abstract Amount price();

  public static Builder newBuilder() {
    return new AutoValue_Seat.Builder();
  }


  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setSeatNumber(String value);

    public abstract Builder setIsAvailable(boolean value);

    public abstract Builder setSeatType(SeatType value);

    public abstract Builder setPrice(Amount value);

    public abstract Seat build();
  }
}
