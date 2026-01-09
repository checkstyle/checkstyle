/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocTagContinuationIndentation">
      <property name="offset" value="2"/>
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
class Example2 {

  /**
   * @tag comment
   *     Indentation spacing is 4
   */
  public void testMethod1(String input) {
    // ok, Indentation above 1 is fine as offset value is 2
  }

  /**
   * @tag comment
   *   Indentation spacing is 2
   */
  public void testMethod2(String input) {
    // ok, Indentation above 1 is fine as offset value is 2
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
