/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocLeadingAsteriskAlign" />
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocleadingasteriskalign;
// xdoc section -- start
// violation 2 lines below '1, expected is 2.'
/**
* Javadoc for class.
 */
public class Example1 {
  // violation 2 lines below '5, expected is 4.'
  /**
    * Javadoc for instance variable, over-indented.
   */
  private int wrongIndentField;

  /**
   * Javadoc for instance variable, correctly aligned.
   */
  private int goodIndentField;
  // violation 2 lines below '3, expected is 4.'
  /**
  *  Javadoc for method, under-indented.
  */
  private void wrongIndentMethod() {}
  // violation 2 lines above '3, expected is 4.'
  /**
   * Javadoc for method, correctly aligned.
   */
  private void goodIndentMethod() {}
  // violation 3 lines below '1, expected is 4.'
  /**
   Javadoc for constructor, missing leading asterisk alignment on closing tag.
*/
  private Example1() {}

  /**
   * Javadoc for constructor, correctly aligned.
   */
  public Example1(int value) {}

  private enum indentedEnum {
    // violation 2 lines below '5, expected is 6.'
    /**
    *  Wrong alignment for enum constant.
     */
    WRONG,

    /**
     * Correct alignment for enum constant.
     */
    GOOD
  }
}
// xdoc section -- end
