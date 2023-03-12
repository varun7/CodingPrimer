package org.code.runs.ooo.common.models;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Charge {
  public abstract String userId();
  public abstract String paymentMethodId();
  public abstract Amount amount();

  public static Builder newBuilder() {
    return new AutoValue_Charge.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setUserId(String value);

    public abstract Builder setPaymentMethodId(String value);

    public abstract Builder setAmount(Amount value);

    public abstract Charge build();
  }
}
