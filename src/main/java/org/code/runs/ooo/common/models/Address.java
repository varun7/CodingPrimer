package org.code.runs.ooo.common.models;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Address {
  public abstract String plotNumber();
  public abstract String street();
  public abstract String city();
  public abstract String pinCode();
  public abstract String country();

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setPlotNumber(String value);

    public abstract Builder setStreet(String value);

    public abstract Builder setCity(String value);

    public abstract Builder setPinCode(String value);

    public abstract Builder setCountry(String value);

    public abstract Address build();
  }
}
