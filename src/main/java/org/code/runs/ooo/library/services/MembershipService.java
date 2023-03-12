package org.code.runs.ooo.library.services;

import java.util.HashSet;
import java.util.Set;
import org.code.runs.ooo.library.models.Member;

public interface MembershipService {

  void addMember(Member member);

  void removeMember(Member member);
}

class SimpleMembershipService implements MembershipService {
  private final Set<Member> members;

  public SimpleMembershipService() {
    this.members = new HashSet<>();
  }

  @Override
  public void addMember(Member member) {
    members.add(member);
  }

  @Override
  public void removeMember(Member member) {
    members.remove(member);
  }
}