package org.code.runs.ooo.broker.services;

import java.util.Set;
import org.code.runs.ooo.broker.models.Order;

public interface OrderService {

  Order createOrder(String userId, Order order);

  void cancelOrder(String userId, String orderId);

  Set<Order> history(String userId);

  void executeOrder(String orderId);
}
