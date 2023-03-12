package org.code.runs.ooo.common.models;

import org.code.runs.ooo.hotel.model.PaymentMethodType;

public final class CreditCard extends PaymentMethod {
  private final String encryptedCardNumber;
  private final String encryptedCvv;
  private final Address billingAddress;

  public CreditCard(String encryptedPaymentInstrument, String paymentMethodId,
      PaymentMethodType paymentMethodType,
      String encryptedCardNumber, String encryptedCvv,
      Address billingAddress) {
    super(encryptedPaymentInstrument, paymentMethodId, paymentMethodType);
    this.encryptedCardNumber = encryptedCardNumber;
    this.encryptedCvv = encryptedCvv;
    this.billingAddress = billingAddress;
  }

  public String getEncryptedCardNumber() {
    return encryptedCardNumber;
  }

  public String getEncryptedCvv() {
    return encryptedCvv;
  }

  public Address getBillingAddress() {
    return billingAddress;
  }

}
