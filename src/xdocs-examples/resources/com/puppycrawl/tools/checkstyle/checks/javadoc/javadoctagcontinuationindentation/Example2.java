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
class Example2 {

  /**
   * @param input comment with
   *     indentation spacing for the tag
   */
  public void testIndentation4(String input) {}
   // ok, Indentation above 1 is fine as offset value is 2

  /**
   * @param input comment with
   *   indentation spacing for the tag
   */
  public void testIndentation2(String input) {}
   // ok, Indentation above 1 is fine as offset value is 2

  /**
   * @param input <a> tag is not closed
   *     indentation spacing for the tag
   */
  public void testUnclosedTag(String input) {}

  /**
   * @param input comment with
   *  insufficient indentation spacing
   */
  public void testIndentation1(String input) {}
   // violation 3 lines above 'Line continuation have incorrect indentation level'
}
// xdoc section -- end
