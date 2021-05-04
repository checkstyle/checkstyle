/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocLeadingAsteriskAlign" />
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocleadingasteriskalign;

/**
* This is the class level javadoc.
 * // violation above 'Leading asterisk has .* indentation .* 1, expected is 2.'
 */
public class InputJavadocLeadingAsteriskAlignIncorrect {
  /**
    * Javadoc for instance variable
   * // violation above 'Leading asterisk has .* indentation .* 5, expected is 4.'
   */
  private int age;

  /**
  *  // violation 'Leading asterisk has .* indentation .* 3, expected is 4.'
   */
  private String name;

  /**
   * Javadoc for foo.
    */ // violation 'Leading asterisk has .* indentation .* 5, expected is 4.'
  public void foo() {}

  /**
  */ // violation 'Leading asterisk has .* indentation .* 3, expected is 4.'
  public void foo2() {}

  /**
      * // violation 'Leading asterisk has .* indentation .* 7, expected is 4.'
   */
  public void foo3() {}

  /**
*   // violation 'Leading asterisk has .* indentation .* 1, expected is 4.'
   */
  public void foo4() {}

  /**
   * Default Constructor.
      */ // violation 'Leading asterisk has .* indentation .* 7, expected is 4.'
  public InputJavadocLeadingAsteriskAlignIncorrect() {}

  /**
   * Parameterized Constructor.
*/ // violation 'Leading asterisk has .* indentation .* 1, expected is 4.'
  public InputJavadocLeadingAsteriskAlignIncorrect(String a) {}

  /**
      * // violation 'Leading asterisk has .* indentation .* 7, expected is 4.'
    * Inner Class. */ // violation 'Leading asterisk has .* indentation .* 5, expected is 4.'
  private static class Inner {
    /**

        */ // violation 'Leading asterisk has .* indentation .* 9, expected is 6.'
    private Object obj;

    /**
     * @param testing
       *         Testing......
     * // violation above 'Leading asterisk has .* indentation .* 8, expected is 6.'
     */
    void foo(String testing) {}
  }

  private enum incorrectJavadocEnum {

    /**
   */ // violation 'Leading asterisk has .* indentation .* 4, expected is 6.'
    ONE,


    /**
      * Wrong Alignment */ // violation 'Leading asterisk has .* indentation .* 7, expected is 6.'
    TWO
  }
}
