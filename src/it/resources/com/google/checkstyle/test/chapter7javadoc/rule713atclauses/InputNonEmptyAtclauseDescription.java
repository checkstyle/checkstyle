package com.google.checkstyle.test.chapter7javadoc.rule713atclauses;

class InputNonEmptyAtclauseDescription {
  /**
   * Some javadoc.
   *
   * @param a Some javadoc
   * @param b Some javadoc
   * @param c Some javadoc
   * @return Some javadoc
   * @throws Exception Some javadoc
   * @deprecated Some javadoc
   */
  public int foo1(String a, int b, double c) throws Exception {
    return 1;
  }

  /**
   * Some javadoc.
   *
   * @param a Some javadoc
   * @param b Some javadoc
   * @param c Some javadoc
   * @return Some javadoc
   * @throws Exception Some javadoc
   */
  public int foo2(String a, int b, double c) throws Exception {
    return 1;
  }

  // violation 9 lines below 'At-clause should have a non-empty description.'
  // violation 9 lines below 'At-clause should have a non-empty description.'
  // violation 9 lines below 'At-clause should have a non-empty description.'
  // violation 9 lines below 'At-clause should have a non-empty description.'
  // violation 9 lines below 'At-clause should have a non-empty description.'
  // violation 9 lines below 'At-clause should have a non-empty description.'
  /**
   * Some javadoc.
   *
   * @param a
   * @param b
   * @param c
   * @throws Exception
   * @deprecated
   * @deprecated
   */
  public int foo3(String a, int b, double c) throws Exception {
    return 1;
  }

  // violation 8 lines below 'At-clause should have a non-empty description.'
  // violation 8 lines below 'At-clause should have a non-empty description.'
  // violation 8 lines below 'At-clause should have a non-empty description.'
  // violation 8 lines below 'At-clause should have a non-empty description.'
  // violation 8 lines below 'At-clause should have a non-empty description.'
  /**
   * Some javadoc.
   *
   * @param a
   * @param b
   * @param c
   * @throws Exception
   * @deprecated
   */
  public int foo4(String a, int b, double c) throws Exception {
    return 1;
  }
}
