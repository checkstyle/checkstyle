/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocLeadingAsteriskAlign">
      <property name="indentation" value="0"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocleadingasteriskalign;
// xdoc section - start
/**
* Javadoc for class, aligned under the slash.
*/
public class Example2 {

  /**
  * Javadoc for instance variable, aligned under the slash.
  */
  private int goodIndentField;

  // violation 2 lines below '1, expected is 0.'
  /**
   * Javadoc for method, aligned under the first asterisk.
   */
  private void wrongIndentMethod() {}
  // violation 2 lines above '1, expected is 0.'

  /**
  * Javadoc for constructor, aligned under the slash.
  */
  public Example2() {}
}
// xdoc section - end
