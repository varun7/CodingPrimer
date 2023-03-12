package org.code.runs.ooo.common.services;

import java.util.HashMap;
import java.util.Map;
import org.code.runs.ooo.common.models.User;

public class SimpleIdentityService implements IdentityService {
  private final Map<String, User> users;
  public SimpleIdentityService() {
    users = new HashMap<>();
  }

  @Override
  public void createUser(User user)  {
    if (users.containsKey(user)) {
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
}