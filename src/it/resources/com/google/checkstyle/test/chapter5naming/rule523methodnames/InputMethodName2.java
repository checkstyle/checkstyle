package com.google.checkstyle.test.chapter5naming.rule523methodnames;

import org.junit.jupiter.api.Test;

/** Some data. */
public class InputMethodName2 {

  @Test
  public void transferMoney_deductsFromSource() {}

  @Test // violation below, 'Method name .* is not valid per Google Java Style Guide.'
  public void transferMoney_DeductsFromSource() {}

  @Test
  public void solve6x6_returnsTrue() {}

  @Test // violation below, ''solve6x6_ReturnsTrue' is not valid per Google Java Style Guide.'
  public void solve6x6_ReturnsTrue() {}

  @Test
  public void solve6x6_noSolution_returnsFalse() {}

  @Test // violation below, 'Method name .* is not valid per Google Java Style Guide.'
  public void solve6x6_NoSolution_ReturnsFalse() {}

  @Test
  public void openMenu_deletePreviousView() {}

  @Test // violation below, 'Method name .* is not valid per Google Java Style Guide.'
  public void openMenu_DeletePreviousView() {}

  @Test
  public void test_general_logic() {}

  @Test
  public void test_General_Logic() {}
  // violation above, 'Method name 'test_General_Logic' is not valid per Google Java Style Guide.'
}
