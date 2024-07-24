package com.google.checkstyle.test.chapter2filebasic.rule231filetab;

final class InputWhitespaceCharacters {
  // Long line ----------------------------------------------------------------
  // Contains a tab ->	<- // violation 'Line contains a tab character.'
  // Contains trailing whitespace ->

  /** Invalid format. * */
  public static final int badconstant = 2;

  /** Valid format. * */
  public static final int MAX_ROWS = 2;

  /** Invalid format. * */
  private static int badstatic = 2;

  /** Valid format. * */
  private static int snumcreated = 0;

  /** Invalid format. * */
  private int badmember = 2;

  /** Valid format. * */
  private int mnumcreated1 = 0;

  /** Valid format. * */
  protected int mnumcreated2 = 0;

  /** commas are wrong. * */
  private int[] mints = new int[] {1, 2, 3, 4};

  public static int stest1;

  /** should be private. * */
  protected static int stest3;

  /** should be private. * */
  static int stest2;

  /** should be private. * */
  int mtest1;

  /** should be private. * */
  public int mtest2;

  /**
   * Some javadoc.
   *
   * @param badFormat1 bad format
   * @param badFormat2 bad format
   * @param badFormat3 bad format
   * @return hack
   * @throws java.lang.Exception abc
   **/
  int test1(int badFormat1, int badFormat2,
  		final int badFormat3) // violation 'Line contains a tab character.'
      throws java.lang.Exception {
    return 0;
  }

  /** method that is 20 lines long. * */
  private void longMethod() {
    // a line
    // a line
    // a line
    // a line
    // a line
    // a line
    // a line
    // a line
    // a line
    // a line
    // a line
    // a line
    // a line
    // a line
    // a line
    // a line
    // a line
    // a line
  }

  /** constructor that is 10 lines long. * */
  private InputWhitespaceCharacters() {
    // a line
    // a line
    // a line
    // a line
    // a line
    // a line
    // a line
    // a line
  }

  /** test local variables. */
  private void localVariables() {
    int abc = 0;
    int pqr = 0;

    final int cde = 0;
    final int xyz = 0;

    for (int testing = 0; testing < 1; testing++) {
      String innerBlockVariable = "";
    }
    for (int testing = 0; testing < 1; testing++) {
      String innerblockvariable = "";
    }
  }

  /** test method pattern. */
  void ALL_UPPERCASE_METHOD() {}
  // 2 violations above:
  //  'Abbreviation in name .* must contain no more than '1' consecutive capital letters.'
  //  'Method name 'ALL_UPPERCASE_METHOD' must match pattern.'

  /** test illegal constant. * */
  private static final int BAD__NAME = 3;

  // A very, very long line that is OK because it matches the regexp "^.*is OK.*regexp.*$"
  // long line that has a tab ->	<- and would be OK if tab counted as 1 char
  // violation above 'Line contains a tab character.'
  // tabs that count as one char because of their position ->	<-   ->	<-
  // violation above 'Line contains a tab character.'

  /** some lines to test the column after tabs. */
  void violateColumnAfterTabs() {
    // with tab-width 8 all statements below start at the same column,
    // with different combinations of ' ' and '\t' before the statement
    int tab0 = 1;
  	int tab1 = 1;
   // 2 violations above:
   //  'Line contains a tab character.'
   //  ''method def' child has incorrect indentation level 8, expected level should be 4.'
  	int tab2 = 1;
  // 2 violations above:
  //  'Line contains a tab character.'
  //  ''method def' child has incorrect indentation level 8, expected level should be 4.'
		int tab3 = 1;
    // 2 violations above:
    //  'Line contains a tab character.'
    //  ''method def' child has incorrect indentation level 16, expected level should be 4.'
 	 	int tab4 = 1;
   // 2 violations above:
   //  'Line contains a tab character.'
   //  ''method def' child has incorrect indentation level 16, expected level should be 4.'
  	int tab5 = 1;
   // 2 violations above:
   //  'Line contains a tab character.'
   //  ''method def' child has incorrect indentation level 8, expected level should be 4.'
  }

  // MEMME:
  /* MEMME: a
   * MEMME:
   * OOOO
   */
  /* NOTHING */
  /* YES */
  /* MEMME: x */
  /* YES!! */

  /** test long comments. * */
  void veryLong() {
    /*
    blah blah blah blah
    blah blah blah blah
    blah blah blah blah
    blah blah blah blah
    blah blah blah blah
    blah blah blah blah
    blah blah blah blah
    blah blah blah blah
    blah blah blah blah
    blah blah blah blah
    blah blah blah blah
    blah blah blah blah
    blah blah blah blah
    blah blah blah blah
    blah blah blah blah
    enough talk */
  }

  /**
   * Some javadoc.
   *
   * @see to lazy to document all args. Testing excessive # args
   */
  void toManyArgs(
      int arg1,
      int arg2,
      int arg3,
      int arg4,
      int arg5,
      int arg6,
      int arg7,
      int arg8,
      int arg9) {}

  /** Test class for variable naming in for each clause. */
  class InputSimple2 {
    /** Some more Javadoc. */
    public void doSomething() {
      // "O" should be named "o"
      for (Object obj : new java.util.ArrayList()) {}
    }
  }

  /** Test enum for member naming check. */
  enum MyEnum1 {
    /** ABC constant. */
    ABC,

    /** XYZ constant. */
    XYZ;

    /** Should be mSomeMember. */
    private int someMember;
  }
}
