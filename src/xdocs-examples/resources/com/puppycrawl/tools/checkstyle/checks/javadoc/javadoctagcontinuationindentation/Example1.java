/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocTagContinuationIndentation"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctagcontinuationindentation;

// xdoc section -- start
class Example1 {

  /**
   * @param input comment with
   *     indentation spacing for the tag
   */
  public void testIndentation(String input) {}
   // ok, Default expected Indentation is 4

  /**
   * <pre>
   * this content, and not any error:
   *   "JavadocTagContinuation do not validate lines contained in Pre tag,
   *   No violation is expected here."</pre>
   */
  public void testMethodPre(String input) {}

  /**
   * Writes the object using a
   * <a href="{@docRoot}/serialized-form.html#java.time.Ser">dedicated form</a>.
   * @serialData
   * <code> // violation
   * out.writeByte(1); // violation
   * out.writeInt(nanos); // violation
   * </code> // violation
   */
  public void testMethodCode(String input) {}

  /**
   * Test class.
   *
   * @param input comment with
   *          This is the predefined indentation applied by Eclipse formatter.
   */
  public void testIndentationEclipse(String input) {}
}
// xdoc section -- end
