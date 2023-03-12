package org.code.runs.ooo.common.models;

import com.google.auto.value.AutoValue;
import java.util.List;
import java.util.Set;

@AutoValue
public abstract class User {
  public abstract String userId();
  public abstract String firstName();
  public abstract String lastName();

  public static Builder newBuilder() {
    return new AutoValue_User.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setUserId(String value);

    public abstract Builder setFirstName(String value);

    public abstract Builder setLastName(String value);

    public abstract User build();
  }
}
