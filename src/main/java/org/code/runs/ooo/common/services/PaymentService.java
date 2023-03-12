package org.code.runs.ooo.common.services;

import java.util.Set;
import org.code.runs.ooo.common.models.Amount;
import org.code.runs.ooo.common.models.Charge;
import org.code.runs.ooo.common.models.PaymentMethod;

public interface PaymentService {
  void addPaymentMethod(String userId, PaymentMethod paymentMethod);

  void deletePaymentMethod(String userId, String paymentMethodId);

  Set<PaymentMethod> availablePaymentMethods(String userId);

  Charge charge(String userId, String paymentMethodId, Amount amount);
}
