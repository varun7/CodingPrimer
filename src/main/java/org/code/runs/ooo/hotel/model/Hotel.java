package org.code.runs.ooo.hotel.model;

import com.google.auto.value.AutoValue;
import java.util.Set;

@AutoValue
public abstract class Hotel {
  public abstract String hotelId();
  public abstract String name();
  public abstract Address address();
  public abstract Set<Room> rooms();

  public static Builder newBuilder() {
    return new AutoValue_Hotel.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setHotelId(String value);

    public abstract Builder setName(String value);

    public abstract Builder setAddress(Address value);

    public abstract Builder setRooms(Set<Room> value);

    public abstract Hotel build();
  }
}
