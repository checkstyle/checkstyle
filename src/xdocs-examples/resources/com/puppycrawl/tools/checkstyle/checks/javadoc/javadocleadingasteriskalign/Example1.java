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
   Javadoc for method. */
  private void foo() {}

  /**
    Javadoc for Constructor.
    This javadoc is allowed because there is no leading asterisk.
   */
  public Example1() {}

  /**
   * Javadoc for enum.
   */
  private enum correctJavadocEnum {
    /**
     * Correct Indentation for leading asterisk */
    ONE,

    /**
     Allowed because there are non-whitespace characters before the ending block. */
    TWO
  }
}
// xdoc section -- end
