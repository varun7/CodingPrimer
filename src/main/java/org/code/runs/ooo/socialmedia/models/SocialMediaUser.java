package org.code.runs.ooo.socialmedia.models;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class SocialMediaUser implements Entity {

  public abstract String entityId();
  public abstract EntityType entityType();
  public abstract String firstName();

  public static Builder newBuilder() {
    return new AutoValue_SocialMediaUser.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setEntityId(String value);

    public abstract Builder setEntityType(EntityType value);

    public abstract Builder setFirstName(String value);

    public abstract SocialMediaUser build();
  }
}
