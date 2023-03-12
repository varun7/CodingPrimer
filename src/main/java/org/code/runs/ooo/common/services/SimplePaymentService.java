package org.code.runs.ooo.common.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.code.runs.ooo.common.models.Amount;
import org.code.runs.ooo.common.models.Charge;
import org.code.runs.ooo.common.models.PaymentMethod;

public class SimplePaymentService implements PaymentService {
  private Map<String, PaymentMethod> paymentMap;
  private Map<String, Set<String>> userPaymentMap;

  public SimplePaymentService() {
    paymentMap = new HashMap<>();
    userPaymentMap = new HashMap<>();
  }

  @Override
  public void addPaymentMethod(String userId, PaymentMethod paymentMethod) {
    if (paymentMap.containsKey(paymentMethod.getPaymentMethodId())) {
      throw new IllegalArgumentException("Payment method already exists");
    }
    paymentMap.put(paymentMethod.getPaymentMethodId(), paymentMethod);
    Set<String> paymentMethodIds = userPaymentMap.getOrDefault(userId, new HashSet<>());
    paymentMethodIds.add(paymentMethod.getPaymentMethodId());
    userPaymentMap.put(userId, paymentMethodIds);
  }

  @Override
  public void deletePaymentMethod(String userId, String paymentMethodId) {
    if (!paymentMap.containsKey(paymentMethodId)) {
      throw new IllegalArgumentException("Payment method is not present.");
    }
    paymentMap.remove(paymentMethodId);
    userPaymentMap.remove(userId);
    userPaymentMap.remove(userId);
  }

  @Override
  public Set<PaymentMethod> availablePaymentMethods(String userId) {
    return userPaymentMap.get(userId)
        .stream()
        .map(paymentMap::get)
        .collect(Collectors.toSet());
  }

  @Override
  public Charge charge(String userId, String paymentMethodId, Amount amount) {
    // Talks to payment processor and creates a charge
    return Charge.newBuilder()
        .setAmount(amount)
        .setUserId(userId)
        .setPaymentMethodId(paymentMethodId)
        .build();
  }
}
