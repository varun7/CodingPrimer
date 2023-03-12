package org.code.runs.ooo.hotel.model;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Reservation {
  public abstract String reservationId();
  public abstract Amount totalAmount();
  public abstract ReservationStatus reservationStatus();

  public static Builder newBuilder() {
    return new AutoValue_Reservation.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setReservationId(String value);

    public abstract Builder setTotalAmount(Amount value);

    public abstract Builder setReservationStatus(ReservationStatus value);

    public abstract Reservation build();
  }
}
