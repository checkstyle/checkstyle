package com.google.checkstyle.test.chapter7javadoc.rule711generalform;

/** Extra violations are kept in this file to cover edge cases. */
public class InputFormattedIncorrectJavadocLeadingAsteriskAlignment {
  /** Javadoc for instance variable. */
  private int age;

  /** Misaligned leading asterisk. */
  private String name;

  /** Javadoc for foo. */
  public void foo() {}

  // violation below "Summary javadoc is missing."
  /** */
  public void foo2() {}

  /** Misaligned leading asterisk. */
  public void foo3() {}

  /** Misaligned leading asterisk. */
  public void foo4() {}

  /** Default Constructor. */
  public InputFormattedIncorrectJavadocLeadingAsteriskAlignment() {}

  /** Parameterized Constructor. */
  public InputFormattedIncorrectJavadocLeadingAsteriskAlignment(String a) {}

  /** Misaligned leading asterisk. Inner Class. */
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
