package org.code.runs.ooo.ticketmaster.models;

import com.google.auto.value.AutoValue;
import java.util.List;
import org.code.runs.ooo.common.models.Address;

@AutoValue
public abstract class Theater {
  public abstract String name();
  public abstract Address address();
  public abstract List<Auditorium> auditoriums();

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setName(String value);

    public abstract Builder setAddress(Address value);

    public abstract Builder setAuditoriums(List<Auditorium> value);

    public abstract Theater build();
  }
}
