package org.code.runs.ooo.library.models;

public class Member extends Person {

  private final String memberId;

  public Member(String name, String address, String phoneNumber, String emailId,
      String memberId) {
    super(name, address, phoneNumber, emailId);
    this.memberId = memberId;
  }

  public String getMemberId() {
    return memberId;
  }

  @Override
  public String toString() {
    return "Member{" +
        "memberId='" + memberId + '\'' +
        '}' + super.toString();
  }
}
