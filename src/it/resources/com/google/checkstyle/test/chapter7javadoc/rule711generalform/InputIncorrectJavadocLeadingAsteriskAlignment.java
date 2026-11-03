package com.google.checkstyle.test.chapter7javadoc.rule711generalform;

// violation 2 lines below 'Leading asterisk has .* indentation .* 0, expected is 1.'
/**
* Extra violations are kept in this file to cover edge cases.
 */
public class InputIncorrectJavadocLeadingAsteriskAlignment {
  // violation 2 lines below 'Leading asterisk has .* indentation .* 2, expected is 1.'
  /**
    * Javadoc for instance variable.
   */
  private int age;

  // violation 2 lines below 'Leading asterisk has .* indentation .* 0, expected is 1.'
  /**
  *  Misaligned leading asterisk.
   */
  private String name;

  // violation 3 lines below 'Leading asterisk has .* indentation .* 2, expected is 1.'
  /**
   * Javadoc for foo.
    */
  public void foo() {}

  // violation 2 lines below "Summary javadoc is missing."
  // violation 2 lines below 'Leading asterisk has .* indentation .* 0, expected is 1.'
  /**
  */
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

  // violation 3 lines below 'Leading asterisk has .* indentation .* 4, expected is 1.'
  /**
   * Default Constructor.
      */
  public InputIncorrectJavadocLeadingAsteriskAlignment() {}

  // violation 3 lines below 'Leading asterisk has .* indentation .* -2, expected is 1.'
  /**
   * Parameterized Constructor.
*/
  public InputIncorrectJavadocLeadingAsteriskAlignment(String a) {}

  // violation 3 lines below 'Leading asterisk has .* indentation .* 4, expected is 1.'
  // violation 3 lines below 'Leading asterisk has .* indentation .* 2, expected is 1.'
  /**
      * Misaligned leading asterisk.
    * Inner Class. */
  private static class Inner {
    // violation 3 lines below "Summary javadoc is missing."
    // violation 3 lines below "Javadoc line should start with leading asterisk."
    // violation 3 lines below 'Leading asterisk has .* indentation .* 4, expected is 1.'
    /**

        */
    private Object obj;

    // violation 2 lines below "Summary javadoc is missing."
    // violation 3 lines below 'Leading asterisk has .* indentation .* 3, expected is 1.'
    /**
     * @param testing
       *         Testing......
     *
     */
    void foo(String testing) {}
  }

  private enum IncorrectJavadocEnum {

    // violation 2 lines below "Summary javadoc is missing."
    // violation 2 lines below 'Leading asterisk has .* indentation .* -1, expected is 1.'
    /**
   */
    ONE,


    // Closing tag should be alone on line. False negative until #18273
    // violation 2 lines below 'Leading asterisk has .* indentation .* 2, expected is 1'
    /**
      * Wrong Alignment. */
    TWO
  }
}
