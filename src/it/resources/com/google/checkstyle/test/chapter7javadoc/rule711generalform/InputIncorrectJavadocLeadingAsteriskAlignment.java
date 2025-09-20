package com.google.checkstyle.test.chapter7javadoc.rule711generalform;

/**
* Extra violations are kept in this file to cover edge cases.
 * // violation above 'Leading asterisk has .* indentation .* 1, expected is 2.'
 */
public class InputIncorrectJavadocLeadingAsteriskAlignment {
  /**
    * Javadoc for instance variable.
   * // violation above 'Leading asterisk has .* indentation .* 5, expected is 4.'
   */
  private int age;

  // violation below "First sentence of Javadoc is missing an ending period."
  /**
  *  // violation 'Leading asterisk has .* indentation .* 3, expected is 4.'
   */
  private String name;

  /**
   * Javadoc for foo.
    */ // violation 'Leading asterisk has .* indentation .* 5, expected is 4.'
  public void foo() {}

  // violation below "Summary javadoc is missing."
  /**
  */ // violation 'Leading asterisk has .* indentation .* 3, expected is 4.'
  public void foo2() {}

  // violation below "First sentence of Javadoc is missing an ending period."
  /**
      * // violation 'Leading asterisk has .* indentation .* 7, expected is 4.'
   */
  public void foo3() {}

  // violation below "First sentence of Javadoc is missing an ending period."
  /**
*   // violation 'Leading asterisk has .* indentation .* 1, expected is 4.'
   */
  public void foo4() {}

  /**
   * Default Constructor.
      */ // violation 'Leading asterisk has .* indentation .* 7, expected is 4.'
  public InputIncorrectJavadocLeadingAsteriskAlignment() {}

  /**
   * Parameterized Constructor.
*/ // violation 'Leading asterisk has .* indentation .* 1, expected is 4.'
  public InputIncorrectJavadocLeadingAsteriskAlignment(String a) {}

  /**
      * // violation 'Leading asterisk has .* indentation .* 7, expected is 4.'
    * Inner Class. */ // violation 'Leading asterisk has .* indentation .* 5, expected is 4.'
  private static class Inner {
    // violation below "Summary javadoc is missing."
    /**

        */ // violation 'Leading asterisk has .* indentation .* 9, expected is 6.'
    private Object obj;

    // violation below "Summary javadoc is missing."
    /**
     * @param testing
       *         Testing......
     *
     */ // violation 2 lines above 'Leading asterisk has .* indentation .* 8, expected is 6.'
    void foo(String testing) {}
  }

  private enum IncorrectJavadocEnum {

    // violation below "Summary javadoc is missing."
    /**
   */ // violation 'Leading asterisk has .* indentation .* 4, expected is 6.'
    ONE,


    /**
      * Wrong Alignment. */ // violation 'Leading asterisk has .* indentation .* 7, expected is 6'
    TWO
  }
}
