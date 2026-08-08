package com.google.checkstyle.test.chapter7javadoc.rule711generalform;

/**
* Extra violations are kept in this file to cover edge cases.
 */
// violation 2 lines above 'Leading asterisk has .* indentation .* 0, expected is 1.'
public class InputIncorrectJavadocLeadingAsteriskAlignment {
  /**
    * Javadoc for instance variable.
   */
  // violation 2 lines above 'Leading asterisk has .* indentation .* 2, expected is 1.'
  private int age;

  /**
  *  Misaligned leading asterisk.
   */
  // violation 2 lines above 'Leading asterisk has .* indentation .* 0, expected is 1.'
  private String name;

  /**
   * Javadoc for foo.
    */
  // violation above 'Leading asterisk has .* indentation .* 2, expected is 1.'
  public void foo() {}

  // violation below "Summary javadoc is missing."
  /**
  */
  // violation above 'Leading asterisk has .* indentation .* 0, expected is 1.'
  public void foo2() {}

  // violation 2 lines below 'Leading asterisk has .* indentation .* 4, expected is 1.'
  /**
      * Misaligned leading asterisk.
   */
  public void foo3() {}

  // violation 2 lines below 'Leading asterisk has .* indentation .* -2, expected is 1.'
  /**
*   Misaligned leading asterisk.
   */
  public void foo4() {}

  /**
   * Default Constructor.
      */
  // violation above 'Leading asterisk has .* indentation .* 4, expected is 1.'
  public InputIncorrectJavadocLeadingAsteriskAlignment() {}

  /**
   * Parameterized Constructor.
*/
  // violation above 'Leading asterisk has .* indentation .* -2, expected is 1.'
  public InputIncorrectJavadocLeadingAsteriskAlignment(String a) {}

  // violation 2 lines below 'Leading asterisk has .* indentation .* 4, expected is 1.'
  /**
      * Misaligned leading asterisk.
    * Inner Class. */
  // violation above 'Leading asterisk has .* indentation .* 2, expected is 1.'
  private static class Inner {
    // violation 2 lines below "Summary javadoc is missing."
    // violation 2 lines below "Javadoc line should start with leading asterisk."
    /**

        */
    // violation above 'Leading asterisk has .* indentation .* 4, expected is 1.'
    private Object obj;

    // violation below "Summary javadoc is missing."
    /**
     * @param testing
       *         Testing......
     *
     */
    // violation 3 lines above 'Leading asterisk has .* indentation .* 3, expected is 1.'
    void foo(String testing) {}
  }

  private enum IncorrectJavadocEnum {

    // violation below "Summary javadoc is missing."
    /**
   */
    // violation above 'Leading asterisk has .* indentation .* -1, expected is 1.'
    ONE,


    // Closing tag should be alone on line. False negative until #18273
    /**
      * Wrong Alignment. */
    // violation above 'Leading asterisk has .* indentation .* 2, expected is 1'
    TWO
  }
}
