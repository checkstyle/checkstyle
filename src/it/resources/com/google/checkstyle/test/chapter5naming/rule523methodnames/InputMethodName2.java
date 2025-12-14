package com.google.checkstyle.test.chapter5naming.rule523methodnames;

import org.junit.jupiter.api.Test;

/** Some data. */
public class InputMethodName2 {

  @Test
  public void transferMoney_deductsFromSource() {}

  @Test // violation below, 'Test method name .* is not valid, each segment must be lowerCamelCase'
  public void transferMoney_DeductsFromSource() {}

  @Test // violation below, 'Test method name .* has invalid underscore usage.*'
  public void solve6x6_returnsTrue() {}

  @Test // violation below, 'Test method name 'solve6x6_ReturnsTrue' has invalid underscore usage.*'
  public void solve6x6_ReturnsTrue() {}

  @Test // violation below, 'Test method name .* has invalid underscore usage.*'
  public void solve6x6_noSolution_returnsFalse() {}

  @Test // violation below, 'Test method name .* has invalid underscore usage.*'
  public void solve6x6_NoSolution_ReturnsFalse() {}

  @Test
  public void openMenu_deletePreviousView() {}

  @Test // violation below, 'Test method name .* is not valid, each segment must be lowerCamelCase'
  public void openMenu_DeletePreviousView() {}

  @Test
  public void test_general_logic() {}

  @Test
  public void test_General_Logic() {}
  // violation above, ''test_General_Logic' is not valid, each segment must be lowerCamelCase.*'
}
