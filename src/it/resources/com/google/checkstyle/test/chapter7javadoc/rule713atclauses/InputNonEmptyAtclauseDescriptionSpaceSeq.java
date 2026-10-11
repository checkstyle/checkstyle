package com.google.checkstyle.test.chapter7javadoc.rule713atclauses;

class InputNonEmptyAtclauseDescriptionSpaceSeq {
  /**
   * Some javadoc.
   *
   * @param a Some javadoc.
   * @param b Some javadoc.
   */
  public InputNonEmptyAtclauseDescriptionSpaceSeq(String a, int b) {}

  /**
   * Some javadoc.
   *
   * @param a Some javadoc.
   * @deprecated Some javadoc.
   */
  public InputNonEmptyAtclauseDescriptionSpaceSeq(String a) {}

  // violation 6 lines below  'At-clause should have a non-empty description.'
  // violation 6 lines below  'At-clause should have a non-empty description.'
  // violation 6 lines below  'At-clause should have a non-empty description.'
  /**
   * Some javadoc.
   *
   * @param a
   * @param b
   * @param c
   */
  public InputNonEmptyAtclauseDescriptionSpaceSeq(String a, int b, double c) {}

  // violation 6 lines below  'At-clause should have a non-empty description.'
  // violation 6 lines below  'At-clause should have a non-empty description.'
  // violation 6 lines below  'At-clause should have a non-empty description.'
  /**
   * Some javadoc.
   *
   * @param a
   * @param e
   * @deprecated
   */
  public InputNonEmptyAtclauseDescriptionSpaceSeq(String a, boolean e) {}
}
