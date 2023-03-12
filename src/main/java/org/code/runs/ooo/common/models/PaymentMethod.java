package org.code.runs.ooo.common.models;

import org.code.runs.ooo.hotel.model.PaymentMethodType;

public abstract class PaymentMethod {
  private final String encryptedPaymentInstrument;
  private final String paymentMethodId;
  private final PaymentMethodType paymentMethodType;

  public PaymentMethod(String encryptedPaymentInstrument, String paymentMethodId,
      PaymentMethodType paymentMethodType) {
    this.encryptedPaymentInstrument = encryptedPaymentInstrument;
    this.paymentMethodId = paymentMethodId;
    this.paymentMethodType = paymentMethodType;
  }

  public String getEncryptedPaymentInstrument() {
    return encryptedPaymentInstrument;
  }

  public String getPaymentMethodId() {
    return paymentMethodId;
  }

  public PaymentMethodType getPaymentMethodType() {
    return paymentMethodType;
  }
}
