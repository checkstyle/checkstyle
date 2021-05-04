/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocLeadingAsteriskAlign" />
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocleadingasteriskalign;

// xdoc section -- start
/** Title
 * Javadoc for class
 */
public class Example1 {

  /** javadoc for instance variable. */
  private int ball;

  /**
   * Javadoc for instance variable
   */
  private int age;

  /**
    * This method does nothing. // violation
   */
  private void foo() {}

  /**
    This javadoc is allowed because there is no leading asterisk.
   */
  public Example1() {}

  /**
   * Javadoc for Constructor.
  */ // violation
  public Example1(String a) {}

  /**
   * Javadoc for foo2
 This is allowed */
  private void foo2() {}

  /**
   * Javadoc for enum // violation
   */
  private enum correctJavadocEnum {
    /**
     * Correct Indentation for leading asterisk */
    FOUR,

    /**
  *       // violation
     */
    FIVE,
  }
}
// xdoc section -- end
