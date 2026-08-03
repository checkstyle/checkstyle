package com.google.checkstyle.test.chapter5naming.rule523methodnames;

import org.junit.jupiter.api.Test;

/** Some data. */
public class InputMethodName2 {

  @Test
  public void transferMoney_deductsFromSource() {}

  @Test
  public void transferMoney_DeductsFromSource() {}

  // violation 2 lines above """Test method name 'transferMoney_DeductsFromSource' segment
  // must be more than a character, start lowercase, and not have a single lowercase
  // followed by uppercase, or consecutive uppercase."""

  @Test
  public void solve6x6_returnsTrue() {}

  @Test
  public void solve6x6_ReturnsTrue() {}

  // violation 2 lines above """Test method name 'solve6x6_ReturnsTrue' has invalid
  // underscore usage, underscore only allowed between letters or between digits."""

  @Test
  public void solve6x6_noSolution_returnsFalse() {}

  @Test
  public void solve6x6_NoSolution_ReturnsFalse() {}

  // violation 2 lines above """Test method name 'solve6x6_NoSolution_ReturnsFalse' has invalid
  // underscore usage, underscore only allowed between letters or between digits."""

  @Test
  public void openMenu_deletePreviousView() {}

  @Test
  public void openMenu_DeletePreviousView() {}

  // violation 2 lines above """Test method name 'openMenu_DeletePreviousView' segment
  // must be more than a character, start lowercase, and not have a single lowercase
  // followed by uppercase, or consecutive uppercase."""

  @Test
  public void test_general_logic() {}

  @Test
  public void test_General_Logic() {}
  // violation above """Test method name 'test_General_Logic' segment
  // must be more than a character, start lowercase, and not have a single lowercase
  // followed by uppercase, or consecutive uppercase."""

}
