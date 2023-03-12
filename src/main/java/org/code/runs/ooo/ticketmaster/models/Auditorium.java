package org.code.runs.ooo.ticketmaster.models;

import com.google.auto.value.AutoValue;
import java.util.Set;

@AutoValue
public abstract class Auditorium {
  public abstract int audiNumber();

  public abstract Set<Seat> seats();

  public static Builder newBuilder() {
    return new AutoValue_Auditorium.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setAudiNumber(int value);

    public abstract Builder setSeats(Set<Seat> value);

    public abstract Auditorium build();
  }
}
