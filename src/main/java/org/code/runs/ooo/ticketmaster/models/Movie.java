package org.code.runs.ooo.ticketmaster.models;

import com.google.auto.value.AutoValue;
import java.time.Period;

@AutoValue
public abstract class Movie {
  public abstract String title();
  public abstract String language();
  public abstract Period duration();

  public static Builder newBuilder() {
    return new AutoValue_Movie.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setTitle(String value);

    public abstract Builder setLanguage(String value);

    public abstract Builder setDuration(Period value);

    public abstract Movie build();
  }
}
