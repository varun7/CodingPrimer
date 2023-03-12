package org.code.runs.ooo.amazon.services;

import org.code.runs.ooo.amazon.models.Address;

public interface IdentityService {
  void addUser();

  void addAddress(String userId, Address address);
}
