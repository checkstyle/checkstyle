/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocLeadingAsteriskAlign" />
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocleadingasteriskalign;
// xdoc section - start
// violation 2 lines below '0, expected is 1.'
/**
* Javadoc for class.
 */
public class Example1 {
  // violation 2 lines below '2, expected is 1.'
  /**
    * Javadoc for instance variable, over-indented.
   */
  private int wrongIndentField;

  /**
   * Javadoc for instance variable, correctly aligned.
   */
  private int goodIndentField;
  // violation 2 lines below '0, expected is 1.'
  /**
  *  Javadoc for method, under-indented.
  */
  private void wrongIndentMethod() {}
  // violation 2 lines above '0, expected is 1.'
  /**
   * Javadoc for method, correctly aligned.
   */
  private void goodIndentMethod() {}
  // violation 3 lines below '-2, expected is 1.'
  /**
   Javadoc for constructor, missing leading asterisk alignment on closing tag.
*/
  private Example1() {}

  /**
   * Javadoc for constructor, correctly aligned.
   */
  public Example1(int value) {}

  private enum indentedEnum {
    // violation 2 lines below '0, expected is 1.'
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
// xdoc section - end
