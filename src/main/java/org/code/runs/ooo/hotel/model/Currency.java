package org.code.runs.ooo.hotel.model;

public enum Currency {
  USD ("usd"),
  INR ("inr");

  private final String currency;
  Currency(String currency) {
    this.currency = currency;
  }

  public String getCurrency() {
    return currency;
  }
}
