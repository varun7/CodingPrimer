package org.code.runs.ooo.socialmedia.models;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Comment implements Entity {

  @Override
  public abstract String entityId();

  @Override
  public abstract EntityType entityType();

  public abstract String parentId();

  public abstract String text();

  public static Builder newBuilder() {
    return new AutoValue_Comment.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setEntityId(String value);

    public abstract Builder setEntityType(EntityType value);

    public abstract Builder setParentId(String value);

    public abstract Builder setText(String value);

    public abstract Comment build();
  }
}
