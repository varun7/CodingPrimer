package org.code.runs.ooo.broker.models;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Stock {
  public abstract StockSymbol symbol();
  public abstract Amount price();
  public abstract StockExchange exchange();

  public static Builder newBuilder() {
    return new AutoValue_Stock.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setSymbol(StockSymbol value);

    public abstract Builder setPrice(Amount value);

    public abstract Builder setExchange(StockExchange value);

    public abstract Stock build();
  }
}
