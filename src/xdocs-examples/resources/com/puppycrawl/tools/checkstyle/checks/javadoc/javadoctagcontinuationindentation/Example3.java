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
class Example3 {

  /**
   * @param input comment with
   *     indentation spacing for the tag
   */
  public void testIndentation4(String input) {}
   // ok, Default expected Indentation is 4

  /**
   * @param input comment with
   *   indentation spacing for the tag
   */
  public void testIndentation2(String input) {}
   // violation 3 lines above 'Line continuation have incorrect indentation level'

  /**
   * @param input <a> tag is not closed
   *     indentation spacing for the tag
   */
  public void testUnclosedTag(String input) {}
   // violation 2 lines above 'Line continuation have incorrect indentation level'

  /**
   * @return <a> tag is not closed
   *   incorrect indentation spacing
   */
  public String testReturnTag(String input) { return ""; }
   // violation 2 lines above 'Line continuation have incorrect indentation level'
}
// xdoc section -- end
