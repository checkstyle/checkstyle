/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocLeadingAsteriskAlign">
      <property name="tabWidth" value="2"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocleadingasteriskalign;

// xdoc section -- start
/**
 * Title
 */
public class Example3 {

  /**
     * Javadoc for instance variable
   * // violation above '6, expected is 4.'
   */
  private int ball;

  /**
  *  // violation '3, expected is 4.'
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

    /** // violation below '8, expected is 6.'
       * Wrong Alignment
   */ // violation '4, expected is 6.'
    TWO
  }
}
// xdoc section -- end
