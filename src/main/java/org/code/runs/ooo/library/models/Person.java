package org.code.runs.ooo.library.models;

public class Person {
  private final String name;
  private final String address;
  private final String phoneNumber;
  private final String emailId;

  public Person(String name, String address, String phoneNumber, String emailId) {
    this.name = name;
    this.address = address;
    this.phoneNumber = phoneNumber;
    this.emailId = emailId;
  }

  public String getName() {
    return name;
  }

  public String getAddress() {
    return address;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public String getEmailId() {
    return emailId;
  }

  @Override
  public String toString() {
    return "Person{" +
        "name='" + name + '\'' +
        ", address='" + address + '\'' +
        ", phoneNumber='" + phoneNumber + '\'' +
        ", emailId='" + emailId + '\'' +
        '}';
  }
}
