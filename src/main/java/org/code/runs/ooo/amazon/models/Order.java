package org.code.runs.ooo.amazon.models;

import com.google.common.collect.ImmutableList;
import java.util.List;

public final class Order {
  private final String orderId;
  private final List<LineItem> lineItems;
  private final PaymentMethod paymentMethod;
  private final OrderStatus status;

  private Order(String orderId, List<LineItem> lineItems,
      PaymentMethod paymentMethod, OrderStatus status) {
    this.orderId = orderId;
    this.lineItems = ImmutableList.copyOf(lineItems);
    this.paymentMethod = paymentMethod;
    this.status = status;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public List<LineItem> getLineItems() {
    return ImmutableList.copyOf(lineItems);
  }

  public PaymentMethod getPaymentMethod() {
    return paymentMethod;
  }

  public String getOrderId() {
    return orderId;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private String orderId;
    private List<LineItem> lineItems;
    private PaymentMethod paymentMethod;
    private OrderStatus status;

    public Builder setOrderId(String orderId) {
      this.orderId = orderId;
      return this;
    }

    public Builder setLineItems(List<LineItem> lineItems) {
      this.lineItems = lineItems;
      return this;
    }

    public Builder setPaymentMethod(PaymentMethod paymentMethod) {
      this.paymentMethod = paymentMethod;
      return this;
    }

    public Builder setOrderStatus(OrderStatus status) {
      this.status = status;
      return this;
    }

    public Order build() {
      return new Order(orderId, lineItems, paymentMethod, status);
    }
  }
}
