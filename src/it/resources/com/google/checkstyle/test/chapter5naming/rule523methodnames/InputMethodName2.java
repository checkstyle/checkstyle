package com.google.checkstyle.test.chapter5naming.rule523methodnames;

import org.junit.jupiter.api.Test;

/** some data. */
public class InputMethodName2 {

  @Test
  public void transferMoney_deductsFromSource() {}

  @Test
  public void transferMoney_DeductsFromSource() {}
  // violation above 'Method name 'transferMoney_DeductsFromSource' must match pattern'

  @Test
  public void solve6x6_ReturnsTrue() {}
  // violation above 'Method name 'solve6x6_ReturnsTrue' must match pattern'

  @Test
  public void solve6x6_returnsTrue() {}

  @Test
  public void solve6x6_NoSolution_ReturnsFalse() {}
  // violation above 'Method name 'solve6x6_NoSolution_ReturnsFalse' must match pattern'

  @Test
  public void solve6x6_noSolution_returnsFalse() {
  }

  @Test
  public void openMenu_DeletePreviousView() {}
  // violation above 'Method name 'openMenu_DeletePreviousView' must match pattern'

  @Test
  public void openMenu_deletePreviousView() {}

  @Test
  public void test_General_Logic() {}
  // violation above 'Method name 'test_General_Logic' must match pattern '

  @Test
  public void test_general_logic() {}
}
