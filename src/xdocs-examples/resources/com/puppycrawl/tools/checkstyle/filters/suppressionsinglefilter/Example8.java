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
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example8 {

  // Checkstyle: Suppress MagicNumberCheck for this line
  private static final int VALUE = 42; // Suppressed MagicNumberCheck here

  /**
   * Method showing suppressed Checkstyle checks
   * for files in the specified directory.
   */
  public void printValue() {
    System.out.println("The value is: " + VALUE);
  }

  public static void main(String[] args) {
    Example8 example = new Example8();
    example.printValue();
  }
}
// xdoc section -- end
