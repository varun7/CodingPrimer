package org.code.runs.ooo.flight.models;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Address {
  public abstract String houseNumber();
  public abstract String streetName();
  public abstract String city();
  public abstract String country();
  public abstract String pinCode();

  public static Builder newBuilder() {
    return new AutoValue_Address.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setHouseNumber(String value);

    public abstract Builder setStreetName(String value);

    public abstract Builder setCity(String value);

    public abstract Builder setCountry(String value);

    public abstract Builder setPinCode(String value);

    public abstract Address build();
  }
}
