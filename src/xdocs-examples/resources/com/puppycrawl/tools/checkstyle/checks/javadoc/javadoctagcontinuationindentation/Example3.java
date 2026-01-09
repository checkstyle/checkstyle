/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocTagContinuationIndentation">
      <property name="violateExecutionOnNonTightHtml" value="true"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctagcontinuationindentation;

// xdoc section -- start
/**
 * <p> 'p' tag is unclosed
 * <p> 'p' tag is closed</p>
 */
class Example3 {
  // violation 4 lines above 'Unclosed HTML tag found: p'
  /**
   * @param input comment with
   *     indentation spacing as 4
   */
  public void testMethod1(String input) {
    // ok, Default expected Indentation is 4
  }

  /**
   * @param input comment with
   *  indentation spacing as 1
   */
  public void testMethod2(String input) {
    // violation 3 lines above 'Line continuation have incorrect indentation level'
  }

  /**
   * Test class.
   *
   * @apiNote
   *          This is the predefined indentation applied by Eclipse formatter.
   */
   public void testMethod3(String input) {}
}
// xdoc section -- end
