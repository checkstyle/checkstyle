/*xml
<module name="Checker">
  <module name="SuppressionSingleFilter">
    <property name="files" value=".*\.java"/>
    <property name="message" value="Name 'log' must match pattern"/>
  </module>
  <module name="TreeWalker">
    <module name="MagicNumber"/>
    <module name="JavadocTagContinuationIndentation"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example10 {
   // Converted to constant to avoid naming violations
  private static final String LOG_FILE = "app.log";

  /**
   * Processes the log file.
   */
  public void processLog() {
    // Uses constant instead of a variable
    System.out.println("Processing log file: " + LOG_FILE);
  }
}
// xdoc section -- end
