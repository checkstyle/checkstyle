/*xml
<module name="Checker">
  <module name="SuppressionSingleFilter">
    <property name="files" value=".*\.java"/>
    <property name="checks" value=".*"/>
  </module>
  <module name="TreeWalker">
    <module name="MagicNumber"/>
    <module name="VisibilityModifier"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example3 {

  // Checkstyle: Suppress MagicNumberCheck for this line
  // Example of a suppressed warning in this file.
  private static final int NUM = 100; // Suppressed MagicNumberCheck here

  /**
   * Simple method to demonstrate checkstyle suppression.
   */
  public void exampleMethod() {
    System.out.println(NUM);
  }

  public static void main(String[] args) {
    Example3 example = new Example3();
    example.exampleMethod();
  }
}
// xdoc section -- end
