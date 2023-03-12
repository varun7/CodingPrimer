package org.code.runs.ooo.ticketmaster.services;

import org.code.runs.ooo.common.models.Amount;
import org.code.runs.ooo.ticketmaster.models.Invoice;
import org.code.runs.ooo.common.models.PaymentMethod;

public interface PaymentService {
  Invoice charge(Amount amount, PaymentMethod paymentMethod);
}
