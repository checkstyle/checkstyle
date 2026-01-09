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
   *     indentation spacing as 4
   */
  public void testIndentation4(String input) {
    // ok, Indentation above 1 is fine as offset value is 2
  }

  /**
   * @param input comment with
   *   indentation spacing as 2
   */
  public void testIndentation2(String input) {
    // ok, Indentation above 1 is fine as offset value is 2
  }
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
   *          predefined indentation applied by Eclipse formatter.
   */
   public void testIndentationEclipse(String input) {}
}
// xdoc section -- end
