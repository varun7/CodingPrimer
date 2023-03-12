package org.code.runs.ooo.broker.models;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Order {
  public abstract String orderId();
  public abstract String userId();
  public abstract OrderStatus orderStatus();
  public abstract OrderType orderType();
  public abstract StockSymbol stock();
  public abstract int lotSize();
  public abstract Amount price();

  public static Builder newBuilder() {
    return new AutoValue_Order.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setOrderId(String value);

    public abstract Builder setUserId(String value);

    public abstract Builder setOrderStatus(OrderStatus value);

    public abstract Builder setOrderType(OrderType value);

    public abstract Builder setStock(StockSymbol value);

    public abstract Builder setLotSize(int value);

    public abstract Builder setPrice(Amount value);

    public abstract Order build();
  }
}
