package org.code.runs.ooo.flight.models;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Invoice {
  public abstract String invoiceId();
  public abstract User payee();
  public abstract Amount totalAmount();
  public abstract Amount convinienceFee();
  public abstract Amount taxes();
  public abstract Amount discount();
  public abstract Amount flightAmount();

  public static Builder newBuilder() {
    return new AutoValue_Invoice.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setInvoiceId(String value);

    public abstract Builder setPayee(User value);

    public abstract Builder setTotalAmount(Amount value);

    public abstract Builder setConvinienceFee(Amount value);

    public abstract Builder setTaxes(Amount value);

    public abstract Builder setDiscount(Amount value);

    public abstract Builder setFlightAmount(Amount value);

    public abstract Invoice build();
  }
}
