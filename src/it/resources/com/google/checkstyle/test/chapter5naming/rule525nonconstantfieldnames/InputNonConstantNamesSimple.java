package com.google.checkstyle.test.chapter5naming.rule525nonconstantfieldnames;

final class InputNonConstantNamesSimple {

  public static final int badConstant = 2;

  public static final int MAX_ROWS = 2;

  // violation below, ''bad\$Static' must .* contain only letters, digits or underscores'
  private int bad$Static = 2;

  private static int sum_Created = 0;

  // violation below, ''bad_Member' .* underscores allowed only between adjacent digits.'
  private int bad_Member = 2;

  // violation below, 'Non-constant field name 'm' must start lowercase, be at least 2 chars'
  private int m = 0;

  // violation below, ''m_M' .* underscores allowed only between adjacent digits.'
  protected int m_M = 0;

  private int[] m$nts = // violation ''m\$nts' must .* contain only letters, digits or underscores'
      new int[] {1, 2, 3, 4};

  public static int sTest1;

  protected static int sTest3;

  static int sTest2;

  // violation below, ''mTest1' must .* avoid single lowercase letter followed by uppercase'
  int mTest1;

  // violation below, ''mTest2' must .* avoid single lowercase letter followed by uppercase'
  public int mTest2;

  // violation below, ''\$mTest2' must .* contain only letters, digits or underscores'
  public int $mTest2;

  // violation below, ''mTes\$t2' must .* contain only letters, digits or underscores'
  public int mTes$t2;

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

    // violation below, ''bad\$Static' must .* contain only letters, digits or underscores'
    private int bad$Static = 2;

    // violation below, ''sum_Created' .* underscores allowed only between adjacent digits.'
    private int sum_Created = 0;

    // violation below, ''bad_Member' .* underscores allowed only between adjacent digits.'
    private int bad_Member = 2;

    // violation below, 'Non-constant field name 'm' must start lowercase, be at least 2 chars'
    private int m = 0;

    // violation below, ''m_M' .* underscores allowed only between adjacent digits.'
    protected int m_M = 0;

    // violation below, ''m\$nts' must .* contain only letters, digits or underscores'
    private int[] m$nts = new int[] {1, 2, 3, 4};

    // violation below, ''mTest1' must .* avoid single lowercase letter followed by uppercase'
    int mTest1;

    // violation below, ''mTest2' must .* avoid single lowercase letter followed by uppercase'
    public int mTest2;

    // violation below, ''\$mTest2' must .* contain only letters, digits or underscores'
    public int $mTest2;

    // violation below, ''mTes\$t2' must .* contain only letters, digits or underscores'
    public int mTes$t2;

    // violation below, ''mTest2\$' must .* contain only letters, digits or underscores'
    public int mTest2$;

    void fooMethod() {
      Foo foo =
          new Foo() {

            // violation below, ''bad\$Static' must .* contain only letters, digits or underscores'
            int bad$Static = 2;

            // violation below, ''sum_Created' .* underscores allowed only between adjacent digits.'
            int sum_Created = 0;

            // violation below, ''bad_Member' .* underscores allowed only between adjacent digits.'
            int bad_Member = 2;

            // violation below, 'Non-constant field name 'm' must start lowercase, be at least 2'
            int m = 0;

            // violation below, ''m_M' .* underscores allowed only between adjacent digits.'
            int m_M = 0;

            // violation below, ''m\$nts' must .* contain only letters, digits or underscores'
            int[] m$nts = new int[] {1, 2, 3, 4};

            // violation below, ''mTest1' .* avoid single lowercase letter followed by uppercase'
            int mTest1;

            // violation below, ''mTest2' .* avoid single lowercase letter followed by uppercase'
            int mTest2;

            // violation below, ''\$mTest2' must .* contain only letters, digits or underscores'
            int $mTest2;

            // violation below, ''mTes\$t2' must .* contain only letters, digits or underscores'
            int mTes$t2;

            // violation below, ''mTest2\$' must .* contain only letters, digits or underscores'
            int mTest2$;

            public void greet() {}
          };
    }
  }
}
