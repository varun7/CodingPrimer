package org.code.runs.ooo.hotel.model;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Room {
  public abstract RoomType roomType();
  public abstract String roomNumber();

  public static Builder newBuilder() {
    return new AutoValue_Room.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setRoomType(RoomType value);

    public abstract Builder setRoomNumber(String value);

    public abstract Room build();
  }
}
