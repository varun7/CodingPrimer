package org.code.runs.ooo.socialmedia.models;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class GraphQuery {
  public abstract String entityId();
  public abstract EdgeType relationsShip();

  public static Builder newBuilder() {
    return new AutoValue_GraphQuery.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setEntityId(String value);

    public abstract Builder setRelationsShip(EdgeType value);

    public abstract GraphQuery build();
  }
}
