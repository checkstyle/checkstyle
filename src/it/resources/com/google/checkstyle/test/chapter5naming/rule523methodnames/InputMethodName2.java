package com.google.checkstyle.test.chapter5naming.rule523methodnames;

import org.junit.jupiter.api.Test;

/** Some data. */
public class InputMethodName2 {

  @Test
  public void transferMoney_deductsFromSource() {}

  @Test
  public void transferMoney_DeductsFromSource() {}
  // violation above, ''transferMoney_DeductsFromSource' is not valid per Google Java Style Guide.'

  @Test
  public void solve6x6_returnsTrue() {}

  @Test
  public void solve6x6_ReturnsTrue() {}
  // violation above, 'Method name 'solve6x6_ReturnsTrue' is not valid per Google Java Style Guide.'

  @Test
  public void solve6x6_noSolution_returnsFalse() {}

  @Test
  public void solve6x6_NoSolution_ReturnsFalse() {}
  // violation above, ''solve6x6_NoSolution_ReturnsFalse' is not valid per Google Java Style Guide.'

  @Test
  public void openMenu_deletePreviousView() {}

  @Test
  public void openMenu_DeletePreviousView() {}
  // violation above, ''openMenu_DeletePreviousView' is not valid per Google Java Style Guide.'

  @Test
  public void test_general_logic() {}

  @Test
  public void test_General_Logic() {}
  // violation above, 'Method name 'test_General_Logic' is not valid per Google Java Style Guide.'
}
