/*xml
<module name="Checker">
  <module name="SuppressionSingleFilter">
    <property name="files" value="[/\\]\..+"/>
    <property name="checks" value=".*"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example3 {

  // Example of a suppressed warning in this file.
  private static final int NUM = 100;

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
