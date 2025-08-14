package com.google.checkstyle.test.chapter5naming.rule523methodnames;

import org.junit.jupiter.api.Test;

/** some data. */
public class InputMethodName2 {

  @Test
  public void transferMoney_deductsFromSource() {}

  @Test
  public void transferMoney_DeductsFromSource() {} // violation 'transferMoney_DeductsFromSource''

  @Test
  public void solve6x6_returnsTrue() {}

  @Test
  public void solve6x6_ReturnsTrue() {} // violation 'Method name 'solve6x6_ReturnsTrue' must'

  @Test
  public void solve6x6_noSolution_returnsFalse() {}

  @Test // violation below ''solve6x6_NoSolution_ReturnsFalse' must match pattern'
  public void solve6x6_NoSolution_ReturnsFalse() {}

  @Test
  public void openMenu_deletePreviousView() {}

  @Test
  public void openMenu_DeletePreviousView() {} // violation ''openMenu_DeletePreviousView' must'

  @Test
  public void test_general_logic() {}

  @Test
  public void test_General_Logic() {} // violation ''test_General_Logic' must match pattern '
}
