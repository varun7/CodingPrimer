package org.code.runs.ooo.socialmedia.models;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Post implements Entity {

  public abstract String entityId();
  public abstract EntityType entityType();
  public abstract String text();

  public static Builder newBuilder() {
    return new AutoValue_Post.Builder();
  }


  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setEntityId(String value);

    public abstract Builder setEntityType(EntityType value);

    public abstract Builder setText(String value);

    public abstract Post build();
  }
}
