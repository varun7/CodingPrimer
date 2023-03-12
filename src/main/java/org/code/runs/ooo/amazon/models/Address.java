package org.code.runs.ooo.amazon.models;

import com.google.common.base.Preconditions;
import java.util.Optional;

public final class Address {
  private final String addressId;
  private final String houseNumber;
  private final Optional<String> street;
  private final String pinCode;
  private final String region;
  private final String city;
  private final String state;
  private final String country;

  public Address(String addressId, String houseNumber, Optional<String> street, String pinCode, String region,
      String city, String state, String country) {
    this.addressId = Preconditions.checkNotNull(addressId, "AddressId cannot be null");
    this.houseNumber = Preconditions.checkNotNull(houseNumber, "House number cannot be null");
    this.street = street;
    this.pinCode = pinCode;
    this.region = region;
    this.city = city;
    this.state = state;
    this.country = country;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public String getAddressId() {
    return addressId;
  }

  public String getHouseNumber() {
    return houseNumber;
  }

  public Optional<String> getStreet() {
    return street;
  }

  public String getPinCode() {
    return pinCode;
  }

  public String getRegion() {
    return region;
  }

  public String getCity() {
    return city;
  }

  public String getState() {
    return state;
  }

  public String getCountry() {
    return country;
  }

  public static class Builder {
    private String addressId;
    private String houseNumber;
    private Optional<String> street = Optional.empty();
    private String pinCode;
    private String region;
    private String city;
    private String state;
    private String country;

    public void setAddressId(String addressId) {
      this.addressId = addressId;
    }

    public Builder setHouseNumber(String houseNumber) {
      this.houseNumber = houseNumber;
      return this;
    }

    public Builder setStreet(String street) {
      this.street = Optional.of(street);
      return this;
    }

    public Builder setPinCode(String pinCode) {
      this.pinCode = pinCode;
      return this;
    }

    public Builder setRegion(String region) {
      this.region = region;
      return this;
    }

    public Builder setCity(String city) {
      this.city = city;
      return this;
    }

    public Builder setState(String state) {
      this.state = state;
      return this;
    }

    public Builder setCountry(String country) {
      this.country = country;
      return this;
    }

    public Address build() {
      return new Address(addressId, houseNumber, street, pinCode, region, city, state, country);
    }
  }
}
