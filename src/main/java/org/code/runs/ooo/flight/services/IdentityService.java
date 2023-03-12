package org.code.runs.ooo.flight.services;

import org.code.runs.ooo.flight.models.PaymentMethod;
import org.code.runs.ooo.flight.models.User;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public interface IdentityService {
  void createUser(User user);

  void deleteUser(String userId);

  void addPaymentMethod(String userId, PaymentMethod paymentMethod);
}

class SimpleIdentityService implements IdentityService {

  private final Map<String, User> users;

  public SimpleIdentityService() {
    users = new HashMap<>();
  }

  @Override
  public void createUser(User user) {
    if (users.containsKey(user.userId())) {
      throw new IllegalArgumentException("User already exists.");
    }
    users.put(user.userId(), user);
  }

  @Override
  public void deleteUser(String userId) {
    if (!users.containsKey(userId)) {
      throw new IllegalArgumentException("No such user in the system");
    }
    users.remove(userId);
  }

  @Override
  public void addPaymentMethod(String userId, PaymentMethod paymentMethod) {
    if (!users.containsKey(userId)) {
      throw new IllegalArgumentException("No such user in the system");
    }
    User user = users.get(userId);
    Set<PaymentMethod> userPaymentMethods = new HashSet<>();
    userPaymentMethods.add(paymentMethod);
    userPaymentMethods.addAll(user.paymentMethods());

    User updatedUser = User.newBuilder()
        .setUserId(user.userId())
        .setPaymentMethods(userPaymentMethods)
        .setName(user.name())
        .build();
    users.put(userId, updatedUser);
  }
}