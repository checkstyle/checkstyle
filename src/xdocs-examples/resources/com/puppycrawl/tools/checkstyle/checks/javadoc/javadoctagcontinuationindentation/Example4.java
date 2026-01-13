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
class Example4 {

  /**
   * <pre>
   * this content, and not any error:
   *   "JavadocTagContinuation do not validate lines contained in Pre tag,
   *   No violation is expected here."</pre>
   */
  public void testMethod1(String input) {}

  /**
   * Writes the object using a
   * <a href="{@docRoot}/serialized-form.html#java.time.Ser">dedicated form</a>.
   * @serialData
   * <code> // violation
   * out.writeByte(1); // violation
   * out.writeInt(nanos); // violation
   * </code> // violation
   */
  public void testMethod2(String input) {}

  /**
   * Test class.
   *
   * @apiNote
   *          This is the predefined indentation applied by Eclipse formatter.
   */
   public void testMethod3(String input) {}
}
// xdoc section -- end
