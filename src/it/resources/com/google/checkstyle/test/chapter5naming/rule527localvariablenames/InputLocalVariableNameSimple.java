package com.google.checkstyle.test.chapter5naming.rule527localvariablenames;

final class InputLocalVariableNameSimple {
  public static final int badConstant = 2;

  public static final int MAX_ROWS = 2;

  private static int badStatic = 2;

  private static int sNumCreated = 0;

  private int badMember = 2;

  private int numCreated1 = 0;

  protected int numCreated2 = 0;

  private int[] ints = new int[] {1, 2, 3, 4};

  /** Test local variables. */
  private void localVariables() {
    // bad examples
    int a;
    int aA; // violation 'Local variable name 'aA' must match pattern'
    int a1_a; // violation 'Local variable name 'a1_a' must match pattern'
    int A_A; // violation 'Local variable name 'A_A' must match pattern'
    int aa2_a; // violation 'Local variable name 'aa2_a' must match pattern'
    int _a; // violation 'Local variable name '_a' must match pattern'
    int _aa; // violation 'Local variable name '_aa' must match pattern'
    int aa_; // violation 'Local variable name 'aa_' must match pattern'
    int aaa$aaa; // violation 'Local variable name .* must match pattern'
    int $aaaaaa; // violation 'Local variable name .* must match pattern'
    int aaaaaa$; // violation 'Local variable name .* must match pattern'

    // good examples
    int aa;
    int aaAa1a;
    int aaAaaAa2a1;
  }

  /** Test local variables. */
  private void localVariablesUnderscoresBetweenDigits() {
    // bad examples
    int a_1; // violation 'Local variable name 'a_1' must match pattern'
    int a222_B; // violation 'Local variable name 'a222_B' must match pattern'
    int a_b; // violation 'Local variable name 'a_b' must match pattern'
    int ab_1_2; // violation 'Local variable name 'ab_1_2' must match pattern'
    int guava_33_4_6; // violation 'Local variable name 'guava_33_4_6' must match pattern'
    int abc3_a; // violation 'Local variable name 'abc3_a' must match pattern'
    int _a; // violation 'Local variable name '_a' must match pattern'
    int aaa22_; // violation 'Local variable name 'aaa22_' must match pattern'

    // good examples
    int a1;
    int a2222B;
    int ab;
    int ab1_2;
    int guava33_4_6;
    int abc3a;
    int a;
    int aaa22;
  }
}
