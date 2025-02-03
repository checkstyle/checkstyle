/*xml
<module name="Checker">
  <module name="SuppressionSingleFilter">
    <property name="files" value="com[\\/]mycompany[\\/]app[\\/]gen[\\/]"/>
    <property name="checks" value=".*"/>
  </module>
  <module name="MagicNumberCheck"/>
  <module name="JavadocMethodCheck"/>
  <module name="FinalClassCheck"/>
  <module name="IndentationCheck"/>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example8 {

  // Variable to demonstrate code that would normally be checked by Checkstyle.
  private static final int VALUE = 42;

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
