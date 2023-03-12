package org.code.runs.ooo.ticketmaster.models;

import com.google.auto.value.AutoValue;
import org.code.runs.ooo.common.models.Amount;

@AutoValue
public abstract class Invoice {
  public abstract String invoiceNumber();
  public abstract Amount amount();

  public static Builder newBuilder() {
    return new AutoValue_Invoice.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setInvoiceNumber(String value);

    public abstract Builder setAmount(Amount value);

    public abstract Invoice build();
  }
}
