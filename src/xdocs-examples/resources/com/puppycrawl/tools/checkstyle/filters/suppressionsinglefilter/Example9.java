/*xml
<module name="Checker">
  <module name="SuppressionSingleFilter">
    <property name="files" value=".*\.java"/>
    <property name="checks" value=".*"/>
  </module>
  <module name="TreeWalker">
    <module name="MagicNumber"/>
    <module name="JavadocTagContinuationIndentation"/>
  </module>
  <module name="LineLength"/>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example9 {

  // A simple class that would normally be subject to the FileLength check.
  private static final int MAX_LENGTH = 500;

  /**
   * A method that could trigger the FileLength check if not suppressed.
   */
  public void longMethod() {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < 1000; i++) {
      builder.append("This is a very long line of code to simulate a large file.");
    }
    System.out.println(builder.toString());
  }

  public static void main(String[] args) {
    Example9 example = new Example9();
    example.longMethod();
  }
}
// xdoc section -- end
