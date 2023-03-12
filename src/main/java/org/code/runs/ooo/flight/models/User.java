package org.code.runs.ooo.flight.models;

import com.google.auto.value.AutoValue;
import java.util.Set;

@AutoValue
public abstract class User {
  public abstract String userId();
  public abstract String name();
  public abstract Set<PaymentMethod> paymentMethods();

  public static Builder newBuilder() {
    return new AutoValue_User.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setUserId(String value);

    public abstract Builder setName(String value);

    public abstract Builder setPaymentMethods(Set<PaymentMethod> value);

    public abstract User build();
  }
}
