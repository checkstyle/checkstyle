package com.google.checkstyle.test.chapter5naming.rule523methodnames;

import static com.google.common.truth.Truth.assertWithMessage;

import org.junit.jupiter.api.Test;

/** some data. */
public class InputMethodName2 {

  @Test
  public void transferMoney_deductsFromSource() throws Exception {
    final String[] expected = {};
    assertWithMessage("testClass").that(expected).isEmpty();
  }

  @Test
  public void transferMoney_DeductsFromSource() throws Exception {
    // violation above 'Method name 'transferMoney_DeductsFromSource' must match pattern'
    final String[] expected = {};
    assertWithMessage("testClass").that(expected).isEmpty();
  }

  @Test
  public void solve6x6_ReturnsTrue() throws Exception {
    // violation above 'Method name 'solve6x6_ReturnsTrue' must match pattern'
    final String[] expected = {};
    assertWithMessage("testClass").that(expected).isEmpty();
  }

  @Test
  public void solve6x6_returnsTrue() throws Exception {
    final String[] expected = {};
    assertWithMessage("testClass").that(expected).isEmpty();
  }

  @Test
  public void solve6x6_NoSolution_ReturnsFalse() throws Exception {
    // violation above 'Method name 'solve6x6_NoSolution_ReturnsFalse' must match pattern'
    final String[] expected = {};
    assertWithMessage("testClass").that(expected).isEmpty();
  }

  @Test
  public void solve6x6_noSolution_returnsFalse() throws Exception {
    final String[] expected = {};
    assertWithMessage("testClass").that(expected).isEmpty();
  }

  @Test
  public void openMenu_DeletePreviousView() throws Exception {
    // violation above 'Method name 'openMenu_DeletePreviousView' must match pattern'
    final String[] expected = {};
    assertWithMessage("testClass").that(expected).isEmpty();
  }

  @Test
  public void openMenu_deletePreviousView() throws Exception {
    final String[] expected = {};
    assertWithMessage("testClass").that(expected).isEmpty();
  }

  @Test
  public void test_General_Logic() throws Exception {
    // violation above 'Method name 'test_General_Logic' must match pattern '
    final String[] expected = {};
    assertWithMessage("testClass").that(expected).isEmpty();
  }

  @Test
  public void test_general_logic() throws Exception {
    final String[] expected = {};
    assertWithMessage("testClass").that(expected).isEmpty();
  }
}
