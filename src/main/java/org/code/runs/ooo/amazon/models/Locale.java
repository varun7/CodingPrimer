package org.code.runs.ooo.amazon.models;

public enum Locale {
  EN_US ("en-US");

  private final String localeValue;

  Locale(String localeValue) {
    this.localeValue = localeValue;
  }

  public String getLocaleValue() {
    return localeValue;
  }
}
