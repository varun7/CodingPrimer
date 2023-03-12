package org.code.runs.ooo.amazon.models;

import com.google.common.base.Preconditions;
import java.time.Instant;

public final class LineItem {
  private final Product product;
  private final int quantity;
  private final Instant creationTime;

  private LineItem(Product product, int quantity) {
    Preconditions.checkArgument(quantity > 0, "Quantity cannot be negative or zero");
    this.product = product;
    this.quantity = quantity;
    this.creationTime = Instant.now();
  }

  public Product getProduct() {
    return product;
  }

  public int getQuantity() {
    return quantity;
  }

  public Instant getCreationTime() {
    return creationTime;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private Product product;
    private int quantity;
    private Instant creationTime;

    public Builder setProduct(Product product) {
      this.product = product;
      return this;
    }

    public Builder setQuantity(int quantity) {
      this.quantity = quantity;
      return this;
    }

    public LineItem build() {
      return new LineItem(product, quantity);
    }
  }
}
