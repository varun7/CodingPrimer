package org.code.runs.ooo.hotel.model;

import com.google.auto.value.AutoValue;
import java.util.Set;

@AutoValue
public abstract class User {
  public abstract Address address();
  public abstract String firstName();
  public abstract String lastName();
  public abstract String email();
  public abstract Set<PaymentMethod> paymentMethods();

  public static Builder newBuilder() {
    return new AutoValue_User.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setAddress(Address value);

    public abstract Builder setFirstName(String value);

    public abstract Builder setLastName(String value);

    public abstract Builder setEmail(String value);

    public abstract Builder setPaymentMethods(Set<PaymentMethod> value);

    public abstract User build();
  }
}
