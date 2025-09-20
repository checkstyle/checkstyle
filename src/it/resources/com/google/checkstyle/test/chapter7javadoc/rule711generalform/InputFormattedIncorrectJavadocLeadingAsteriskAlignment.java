package com.google.checkstyle.test.chapter7javadoc.rule711generalform;

/** Extra violations are kept in this file to cover edge cases. */
public class InputFormattedIncorrectJavadocLeadingAsteriskAlignment {
  /** Javadoc for instance variable. */
  private int age;

  // violation below "Summary javadoc is missing."
  /** */
  private String name;

  /** Javadoc for foo. */
  public void foo() {}

  // violation below "Summary javadoc is missing."
  /** */
  public void foo2() {}

  // violation below "Summary javadoc is missing."
  /** */
  public void foo3() {}

  // violation below "Summary javadoc is missing."
  /** */
  public void foo4() {}

  /** Default Constructor. */
  public InputFormattedIncorrectJavadocLeadingAsteriskAlignment() {}

  /** Parameterized Constructor. */
  public InputFormattedIncorrectJavadocLeadingAsteriskAlignment(String a) {}

  /** Inner Class. */
  private static class Inner {
    // violation below "Summary javadoc is missing."
    /** */
    private Object obj;

    // violation below "Summary javadoc is missing."
    /**
     * @param testing Testing......
     */
    void foo(String testing) {}
  }

  private enum IncorrectJavadocEnum {

    // violation below "Summary javadoc is missing."
    /** */
    ONE,

    /** Wrong Alignment. */
    TWO
  }
}
