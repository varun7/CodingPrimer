package org.code.runs.ooo.library.models;

public enum Currency {
  USD ("usd"),
  INR ("inr");

  private String currency;

  private Currency(String value) {
    this.currency = value;
  }

  public String getCurrency() {
    return currency;
  }
}
