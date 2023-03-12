package org.code.runs.ooo.common.services;

import org.code.runs.ooo.common.models.User;

public interface IdentityService {
  void createUser(User user);

  void deleteUser(String userId);

}
