package org.code.runs.ooo.library.models;

import java.math.BigDecimal;

public final class Amount {
  private final Currency currency;
  private final BigDecimal value;

  public Amount(Currency currency, BigDecimal value) {
    this.currency = currency;
    this.value = value;
  }

  public Currency getCurrency() {
    return currency;
  }

  public BigDecimal getValue() {
    return value;
  }

  @Override
  public String toString() {
    return "Amount{" +
        "currency=" + currency +
        ", value=" + value +
        '}';
  }
}