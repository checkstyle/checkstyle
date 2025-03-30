/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck">
      <property name="max" value="100"/>
    </module>
    <module name="com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocParagraphCheck"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.sariflogger;

/**
 * This is a Javadoc with a <p> tag that should trigger a paragraph violation.
 */
public class InputSarifLoggerErrorColumn0 {
    // Long line to trigger LineLength
    public void veryLongMethodNameThatExceedsLineLengthLimitBecauseItIsReallyReallyReallyReallyLong() {}
}