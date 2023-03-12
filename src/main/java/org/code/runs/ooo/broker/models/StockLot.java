package org.code.runs.ooo.broker.models;

import com.google.auto.value.AutoValue;
import java.time.LocalDate;

@AutoValue
public abstract class StockLot {
  public abstract StockSymbol stock();
  public abstract String lotId();
  public abstract int quantity();
  public abstract Amount buyingPrice();
  public abstract String buyTransactionId();
  public abstract LocalDate transactionDate();

  public static Builder newBuilder() {
    return new AutoValue_StockLot.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setStock(StockSymbol value);

    public abstract Builder setLotId(String value);

    public abstract Builder setQuantity(int value);

    public abstract Builder setBuyingPrice(Amount value);

    public abstract Builder setBuyTransactionId(String value);

    public abstract Builder setTransactionDate(LocalDate value);

    public abstract StockLot build();
  }
}
