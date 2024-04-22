/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocLeadingAsteriskAlign" />
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocleadingasteriskalign;

/**
* This is the class level javadoc. // violation
 */
public class InputJavadocLeadingAsteriskAlignIncorrect {
  /**
    * Javadoc for instance variable // violation
   */
  private int age;

  /**
  *  // violation
   */
  private String name;

  /**
   * Javadoc for foo.
    */ // violation
  public void foo() {}

  /**
  */ // violation
  public void foo2() {}

  /**
      * // violation
   */
  public void foo3() {}

  /**
*   // violation
   */
  public void foo4() {}

  /**
   * Default Constructor.
      */ // violation
  public InputJavadocLeadingAsteriskAlignIncorrect() {}

  /**
   * Parameterized Constructor.
*/ // violation
  public InputJavadocLeadingAsteriskAlignIncorrect(String a) {}

  /**
      * // violation
    * Inner Class. */ // violation
  private static class Inner {
    /**

        */ // violation
    private Object obj;

    /**
     * @param testing
     *         Testing......
     */
    void foo(String testing) {}
  }

  private enum incorrectJavadocEnum {

    /**
   */ // violation
    ONE,


    /**
      * Wrong Alignment */ // violation
    TWO
  }
}
