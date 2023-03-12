package org.code.runs.ooo.ticketmaster.models;

import com.google.auto.value.AutoValue;
import java.util.Set;
import org.code.runs.ooo.common.models.Amount;

@AutoValue
public abstract class Ticket {
  public abstract Amount totalAmount();
  public abstract Set<Seat> seats();
  public abstract Theater theater();
  public abstract Auditorium auditorium();

  public static Builder newBuilder() {
    return new AutoValue_Ticket.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setTotalAmount(Amount value);

    public abstract Builder setSeats(Set<Seat> value);

    public abstract Builder setTheater(Theater value);

    public abstract Builder setAuditorium(Auditorium value);

    public abstract Ticket build();
  }
}
