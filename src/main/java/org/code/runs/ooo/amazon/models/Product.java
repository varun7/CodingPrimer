package org.code.runs.ooo.amazon.models;

public class Product {
  private final String productId;
  private final String title;
  private final String locale;
  private final Amount listPrice;
  private final Amount discount;
  private final Amount sellingPrice;
  private final Amount tax;

  private Product(String productId, String title, String locale, Amount listPrice,
      Amount discount, Amount sellingPrice, Amount tax) {
    this.productId = productId;
    this.title = title;
    this.locale = locale;
    this.listPrice = listPrice;
    this.discount = discount;
    this.sellingPrice = sellingPrice;
    this.tax = tax;
  }

  public String getProductId() {
    return productId;
  }

  public String getTitle() {
    return title;
  }

  public String getLocale() {
    return locale;
  }

  public Amount getListPrice() {
    return listPrice;
  }

  public Amount getDiscount() {
    return discount;
  }

  public Amount getSellingPrice() {
    return sellingPrice;
  }

  public Amount getTax() {
    return tax;
  }

  public Builder newBuilder() {
    return new Builder();
  }

  public class Builder {
    private String productId;
    private String title;
    private String locale;
    private Amount listPrice;
    private Amount discount;
    private Amount sellingPrice;
    private Amount tax;

    public Builder setProductId(String productId) {
      this.productId = productId;
      return this;
    }

    public Builder setTitle(String title) {
      this.title = title;
      return this;
    }

    public Builder setLocale(String locale) {
      this.locale = locale;
      return this;
    }

    public Builder setListPrice(Amount listPrice) {
      this.listPrice = listPrice;
      return this;
    }

    public Builder setDiscount(Amount discount) {
      this.discount = discount;
      return this;
    }

    public Builder setSellingPrice(Amount sellingPrice) {
      this.sellingPrice = sellingPrice;
      return this;
    }

    public Builder setTax(Amount tax) {
      this.tax = tax;
      return this;
    }

    public Product build() {
      return new Product(productId, title, locale, listPrice, discount, sellingPrice, tax);
    }
  }
}
