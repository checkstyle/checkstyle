/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocTagContinuationIndentation"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctagcontinuationindentation;

// xdoc section -- start
/**
 * <p> 'p' tag is unclosed
 * <p> 'p' tag is closed</p>
 */
class Example1 {

  /**
   * @tag comment
   *     Indentation spacing is 4
   */
  public void testMethod1(String input) {
    // ok, Default expected Indentation is 4
  }

  /**
   * @tag comment
   *   Indentation spacing is 2
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
