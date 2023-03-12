package org.code.runs.ooo.socialmedia.models;

import com.google.auto.value.AutoValue;

@AutoValue
public  abstract class Location implements Entity {
  public abstract long latitude();
  public abstract long longitude();
  @Override public abstract String entityId();
  @Override public abstract EntityType entityType();

  public static Builder newBuilder() {
    return new AutoValue_Location.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setLatitude(long value);

    public abstract Builder setLongitude(long value);

    public abstract Builder setEntityId(String value);

    public abstract Builder setEntityType(EntityType value);

    public abstract Location build();
  }
}
