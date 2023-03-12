package org.code.runs.ooo.hotel.model;

import com.google.auto.value.AutoValue;
import java.math.BigDecimal;

@AutoValue
public abstract class Amount {
  public abstract Currency currency();
  public abstract BigDecimal value();

  public static Builder newBuilder() {
    return new AutoValue_Amount.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setCurrency(Currency value);

    public abstract Builder setValue(BigDecimal value);

    public abstract Amount build();
  }
}
