/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocLeadingAsteriskAlign" />
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocleadingasteriskalign;

// xdoc section -- start
/**
 * Title
 */
public class Example2 {

  /**
   * Javadoc for instance variable
   *
   */
  private int ball;

  /**
   *
   */
  private int age;

  /**
   * Javadoc for foo.
   */
  private void foo() {}

  /**
   * Javadoc for enum.
   */
  private enum sampleEnum {
    /**
     */
    ONE,

    /**
     * Wrong Alignment
     */
    TWO
  }
}
// xdoc section -- end
