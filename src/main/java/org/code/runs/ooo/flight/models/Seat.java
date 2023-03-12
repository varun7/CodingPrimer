package org.code.runs.ooo.flight.models;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Seat {
  public abstract String seatNumber();
  public abstract SeatType seatType();
  public abstract boolean isAvaialble();

  public static Builder newBuilder() {
    return new AutoValue_Seat.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setSeatNumber(String value);

    public abstract Builder setSeatType(SeatType value);

    public abstract Builder setIsAvaialble(boolean value);

    public abstract Seat build();
  }
}
