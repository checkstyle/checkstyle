/*
UnusedTryResourceShouldBeUnnamed

*/

// Java21
package com.puppycrawl.tools.checkstyle.checks.coding.unusedtryresourceshouldbeunnamed;

public class InputUnusedTryResourceShouldBeUnnamedVarKinds implements AutoCloseable {
  final static InputUnusedTryResourceShouldBeUnnamedVarKinds r1 =
      new InputUnusedTryResourceShouldBeUnnamedVarKinds();
  final InputUnusedTryResourceShouldBeUnnamedVarKinds r2 =
      new InputUnusedTryResourceShouldBeUnnamedVarKinds();
  static InputUnusedTryResourceShouldBeUnnamedVarKinds r3 =
      new InputUnusedTryResourceShouldBeUnnamedVarKinds();
  InputUnusedTryResourceShouldBeUnnamedVarKinds r4 =
      new InputUnusedTryResourceShouldBeUnnamedVarKinds();
  public static void meth() {
    InputUnusedTryResourceShouldBeUnnamedVarKinds r5 =
        new InputUnusedTryResourceShouldBeUnnamedVarKinds();
    /* static final field - ok */
    // violation below, 'Unused try resource 'r1' should be unnamed'
    try (r1) {}
    /* non-static final field - ok */
    try (r1.r2) {}
    /* static non-final field - wrong */
    // violation below, 'Unused try resource 'r3' should be unnamed'
    try (r3) {
      fail("Static non-final field is not allowed");
    }
    /* non-static non-final field - wrong */
    try (r1.r4) {
      fail("Non-static non-final field is not allowed");
    }
    /* local variable - covered by TwrForVariable1 test */
    /* array components - covered by TwrForVariable2 test */
    /* method parameter - ok */
    method(r5);
    /* constructor parameter - ok */
    InputUnusedTryResourceShouldBeUnnamedVarKinds r6 =
       new InputUnusedTryResourceShouldBeUnnamedVarKinds(r5);
    /* lambda parameter - covered by TwrAndLambda */
    /* exception parameter - ok */
    try {
      throw new ResourceException();
    }
    catch (ResourceException e) {
      // violation below, 'Unused try resource 'e' should be unnamed'
      try (e) {
      }
    }
  }
  public InputUnusedTryResourceShouldBeUnnamedVarKinds() {}
  public InputUnusedTryResourceShouldBeUnnamedVarKinds(
          InputUnusedTryResourceShouldBeUnnamedVarKinds r) {
    // violation below, 'Unused try resource 'r' should be unnamed'
    try (r) {}
  }
  static void method(InputUnusedTryResourceShouldBeUnnamedVarKinds r) {
    /* parameter */
    // violation below, 'Unused try resource 'r' should be unnamed'
    try (r) {}
  }
  static void fail(String reason) {
    throw new RuntimeException(reason);
  }
  public void close() {}
  static class ResourceException extends Exception implements AutoCloseable {
    public void close() {}
  }
}
