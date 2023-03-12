package org.code.runs.ooo.library.services;

import org.code.runs.ooo.library.models.Amount;
import org.code.runs.ooo.library.models.Invoice;
import org.code.runs.ooo.library.models.Member;

public interface PaymentService {
  Invoice charge(Member member, Amount amount);
}

class SimplePaymentService implements PaymentService {

  @Override
  public Invoice charge(Member member, Amount amount) {
    return new Invoice();
  }
}