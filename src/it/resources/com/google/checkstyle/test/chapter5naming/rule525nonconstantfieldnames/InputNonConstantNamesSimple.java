package com.google.checkstyle.test.chapter5naming.rule525nonconstantfieldnames;

final class InputNonConstantNamesSimple {

  public static final int badConstant = 2;

  public static final int MAX_ROWS = 2;

  private int bad$Static = 2;
  // violation above, ''bad\$Static' must .* contain only letters, digits or underscores'

  private static int sum_Created = 0;

  private int bad_Member = 2;
  // violation above, ''bad_Member' .* underscores allowed only between adjacent digits.'

  private int m = 0;
  // violation above, 'Non-constant field name 'm' must start lowercase, be at least 2 chars'

  protected int m_M = 0;
  // violation above, ''m_M' .* underscores allowed only between adjacent digits.'

  private int[] m$nts = // violation ''m\$nts' must .* contain only letters, digits or underscores'
      new int[] {1, 2, 3, 4};

  public static int sTest1;

  protected static int sTest3;

  static int sTest2;

  int mTest1;
  // violation above, ''mTest1' must .* avoid single lowercase letter followed by uppercase'

  public int mTest2;
  // violation above, ''mTest2' must .* avoid single lowercase letter followed by uppercase'

  public int $mTest2;
  // violation above, ''\$mTest2' must .* contain only letters, digits or underscores'

  public int mTes$t2;
  // violation above, ''mTes\$t2' must .* contain only letters, digits or underscores'

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

    private int bad$Static = 2;
    // violation above, ''bad\$Static' must .* contain only letters, digits or underscores'

    private int sum_Created = 0;
    // violation above, ''sum_Created' .* underscores allowed only between adjacent digits.'

    private int bad_Member = 2;
    // violation above, ''bad_Member' .* underscores allowed only between adjacent digits.'

    private int m = 0;
    // violation above, 'Non-constant field name 'm' must start lowercase, be at least 2 chars'

    protected int m_M = 0;
    // violation above, ''m_M' .* underscores allowed only between adjacent digits.'

    private int[] m$nts = new int[] {1, 2, 3, 4};
    // violation above, ''m\$nts' must .* contain only letters, digits or underscores'

    int mTest1;
    // violation above, ''mTest1' must .* avoid single lowercase letter followed by uppercase'

    public int mTest2;
    // violation above, ''mTest2' must .* avoid single lowercase letter followed by uppercase'

    public int $mTest2;
    // violation above, ''\$mTest2' must .* contain only letters, digits or underscores'

    public int mTes$t2;
    // violation above, ''mTes\$t2' must .* contain only letters, digits or underscores'

    public int mTest2$;
    // violation above, ''mTest2\$' must .* contain only letters, digits or underscores'

    void fooMethod() {
      Foo foo =
          new Foo() {

            int bad$Static = 2;
            // violation above, ''bad\$Static' must .* contain only letters, digits or underscores'

            int sum_Created = 0;
            // violation above, ''sum_Created' .* underscores allowed only between adjacent digits.'

            int bad_Member = 2;
            // violation above, ''bad_Member' .* underscores allowed only between adjacent digits.'

            int m = 0;
            // violation above, 'Non-constant field name 'm' must start lowercase, be at least 2'

            int m_M = 0;
            // violation above, ''m_M' .* underscores allowed only between adjacent digits.'

            int[] m$nts = new int[] {1, 2, 3, 4};
            // violation above, ''m\$nts' must .* contain only letters, digits or underscores'

            int mTest1;
            // violation above, ''mTest1' .* avoid single lowercase letter followed by uppercase'

            int mTest2;
            // violation above, ''mTest2' .* avoid single lowercase letter followed by uppercase'

            int $mTest2;
            // violation above, ''\$mTest2' must .* contain only letters, digits or underscores'

            int mTes$t2;
            // violation above, ''mTes\$t2' must .* contain only letters, digits or underscores'

            int mTest2$;
            // violation above, ''mTest2\$' must .* contain only letters, digits or underscores'

            public void greet() {}
          };
    }
  }
}
