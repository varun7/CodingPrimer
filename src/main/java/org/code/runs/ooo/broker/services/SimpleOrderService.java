package org.code.runs.ooo.broker.services;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import java.util.Set;
import java.util.stream.Collectors;
import org.code.runs.ooo.broker.models.Order;

public class SimpleOrderService implements OrderService {

  // OrderId, UserId, Order
  private final Table<String, String, Order> ordersTable;
  private TransactionService transactionService;
  private PortfolioService portfolioService;

  public SimpleOrderService(TransactionService transactionService, PortfolioService portfolioService) {
    this.ordersTable = HashBasedTable.create();
  }

  @Override
  public Order createOrder(String userId, Order order) {
    ordersTable.put(userId, order.orderId(), order);
    return order;
  }

  @Override
  public void cancelOrder(String userId, String orderId) {
    Order order = ordersTable.get(userId, orderId);
    Order updatedOrder = updateOrder(order);
    ordersTable.put(userId, orderId, updatedOrder);
  }

  @Override
  public Set<Order> history(String userId) {
    return ordersTable.column(userId).values().stream().collect(Collectors.toSet());
  }

  @Override
  public void executeOrder(String orderId) {
    Order toBeExecutedOrder = ordersTable.row(orderId)
        .values()
        .stream()
        .findFirst()
        .orElseThrow();
    transactionService.transact(toBeExecutedOrder.userId(), toBeExecutedOrder, null);
  }

  private Order updateOrder(Order order) {
    return order;
  }
}
