/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocLeadingAsteriskAlign" />
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocleadingasteriskalign;

// violation 2 lines below 'Leading asterisk has .* indentation .* 1, expected is 2.'
/**
* This is the class level javadoc.
 *
 */
public class InputJavadocLeadingAsteriskAlignIncorrect {
  // violation 2 lines below 'Leading asterisk has .* indentation .* 5, expected is 4.'
  /**
    * Javadoc for instance variable
   *
   */
  private int age;

  // violation 2 lines below 'Leading asterisk has .* indentation .* 3, expected is 4.'
  /**
  *
   */
  private String name;

  // violation 3 lines below 'Leading asterisk has .* indentation .* 5, expected is 4.'
  /**
   * Javadoc for foo.
    */
  public void foo() {}

  // violation 2 lines below 'Leading asterisk has .* indentation .* 3, expected is 4.'
  /**
  */
  public void foo2() {}

  // violation 2 lines below 'Leading asterisk has .* indentation .* 7, expected is 4.'
  /**
      *
   */
  public void foo3() {}

  // violation 2 lines below 'Leading asterisk has .* indentation .* 1, expected is 4.'
  /**
*
   */
  public void foo4() {}

  // violation 3 lines below 'Leading asterisk has .* indentation .* 7, expected is 4.'
  /**
   * Default Constructor.
      */
  public InputJavadocLeadingAsteriskAlignIncorrect() {}

  // violation 3 lines below 'Leading asterisk has .* indentation .* 1, expected is 4.'
  /**
   * Parameterized Constructor.
*/
  public InputJavadocLeadingAsteriskAlignIncorrect(String a) {}

  // violation 3 lines below 'Leading asterisk has .* indentation .* 7, expected is 4.'
  // violation 3 lines below 'Leading asterisk has .* indentation .* 5, expected is 4.'
  /**
      *
    * Inner Class. */
  private static class Inner {
    // violation 3 lines below 'Leading asterisk has .* indentation .* 9, expected is 6.'
    /**

        */
    private Object obj;

    // violation 3 lines below 'Leading asterisk has .* indentation .* 8, expected is 6.'
    /**
     * @param testing
       *         Testing......
     *
     */
    void foo(String testing) {}
  }

  private enum incorrectJavadocEnum {

    // violation 2 lines below 'Leading asterisk has .* indentation .* 4, expected is 6.'
    /**
   */
    ONE,


    // violation 2 lines below 'Leading asterisk has .* indentation .* 7, expected is 6.'
    /**
      * Wrong Alignment */
    TWO
  }
}
