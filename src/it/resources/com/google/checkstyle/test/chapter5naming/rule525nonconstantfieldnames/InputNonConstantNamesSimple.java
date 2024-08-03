package com.google.checkstyle.test.chapter5naming.rule525nonconstantfieldnames;

final class InputNonConstantNamesSimple {

  public static final int badConstant = 2;

  public static final int MAX_ROWS = 2;

  private int bad$Static = 2; // violation 'Member name 'bad\$Static' must match pattern'

  private static int sum_Created = 0;

  private int bad_Member = 2; // violation 'Member name 'bad_Member' must match pattern'

  private int m = 0; // violation 'Member name 'm' must match pattern'

  protected int m_M = 0; // violation 'Member name 'm_M' must match pattern'

  private int[] m$nts = // violation 'Member name 'm\$nts' must match pattern'
      new int[] {1, 2, 3, 4};

  public static int sTest1;

  protected static int sTest3;

  static int sTest2;

  int mTest1; // violation 'Member name 'mTest1' must match pattern'

  public int mTest2; // violation 'Member name 'mTest2' must match pattern'

  public int $mTest2; // violation 'Member name '\$mTest2' must match pattern'

  public int mTes$t2; // violation 'Member name 'mTes\$t2' must match pattern'

  private void localVariables() {
    int a;
    int aa;
    int aaAa1a;
    int aaAaaAa2a1;
  }

  interface Foo {
    public void greet();
  }

  class InnerClass {
    public static final int badConstant = 2;

    public static final int MAX_ROWS = 2;

    private int bad$Static = 2; // violation 'Member name 'bad\$Static' must match pattern'

    private int sum_Created = 0;
    // violation above 'Member name 'sum_Created' must match pattern'

    private int bad_Member = 2; // violation 'Member name 'bad_Member' must match pattern'

    private int m = 0; // violation 'Member name 'm' must match pattern'

    protected int m_M = 0; // violation 'Member name 'm_M' must match pattern'

    private int[] m$nts = new int[] {1, 2, 3, 4};
    // violation above 'Member name 'm\$nts' must match pattern'

    int mTest1; // violation 'Member name 'mTest1' must match pattern'

    public int mTest2; // violation 'Member name 'mTest2' must match pattern'

    public int $mTest2; // violation 'Member name '\$mTest2' must match pattern'

    public int mTes$t2; // violation 'Member name 'mTes\$t2' must match pattern'

    public int mTest2$; // violation 'Member name 'mTest2\$' must match pattern'

    void fooMethod() {
      Foo foo =
          new Foo() {

            int bad$Static = 2; // violation 'Member name 'bad\$Static' must match pattern'

            int sum_Created = 0;
            // violation above 'Member name 'sum_Created' must match pattern'

            int bad_Member = 2;
            // violation above 'Member name 'bad_Member' must match pattern'

            int m = 0; // violation 'Member name 'm' must match pattern'

            int m_M = 0; // violation 'Member name 'm_M' must match pattern'

            int[] m$nts = new int[] {1, 2, 3, 4};
            // violation above 'Member name 'm\$nts' must match pattern'

            int mTest1; // violation 'Member name 'mTest1' must match pattern'

            int mTest2; // violation 'Member name 'mTest2' must match pattern'

            int $mTest2; // violation 'Member name '\$mTest2' must match pattern'

            int mTes$t2; // violation 'Member name 'mTes\$t2' must match pattern'

            int mTest2$; // violation 'Member name 'mTest2\$' must match pattern'

            public void greet() {}
          };
    }
  }
}
