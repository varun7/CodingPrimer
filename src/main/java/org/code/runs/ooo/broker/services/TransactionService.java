package org.code.runs.ooo.broker.services;

import org.code.runs.ooo.broker.models.Invoice;
import org.code.runs.ooo.broker.models.Order;
import org.code.runs.ooo.broker.models.PaymentMethod;

public interface TransactionService {

  Invoice transact(String userId, Order order, PaymentMethod paymentMethod);

}
