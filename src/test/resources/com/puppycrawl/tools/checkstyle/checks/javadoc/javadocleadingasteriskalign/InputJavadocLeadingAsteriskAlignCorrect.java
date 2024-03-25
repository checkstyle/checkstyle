/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocLeadingAsteriskAlign" />
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocleadingasteriskalign;

/** Title
 * Javadoc for class
 */
public class InputJavadocLeadingAsteriskAlignCorrect {

  /** javadoc for instance variable. */
  private int a;

  /** */
  private int b;

  /***/
  private String str;

  /**
   * This method does nothing.
   */
  private void foo() {}

  /**
    This javadoc is allowed because there is no leading asterisk.
   */
  public InputJavadocLeadingAsteriskAlignCorrect() {}

  /**
   * Javadoc for foo2
 This is allowed */
  private void foo2() {
    // foo2 code goes here
  } /**
     * This is also allowed
     */
  public void foo3() {}

  /**
   * Javadoc for enum
   */
  private enum correctJavadocEnum {
    /**

     */
    ONE,

    /**
     *
     */
    TWO,

    /** This is allowed

     */
    THREE,

    /**
     * Allowed. */
    FOUR
  }
}
