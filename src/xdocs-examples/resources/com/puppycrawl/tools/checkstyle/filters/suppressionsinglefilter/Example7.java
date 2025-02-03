/*xml
<module name="Checker">
  <module name="SuppressionSingleFilter">
    <property name="files" value=".*\.java"/>
    <property name="checks" value=".*"/>
  </module>
  <module name="TreeWalker">
    <module name="MagicNumber"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example7 {

  // Checkstyle: Suppress MagicNumberCheck for this line
  private static final int NUMBER = 10; // Suppressed MagicNumberCheck here

  /**
   * Method that demonstrates checkstyle suppression.
   */
  public void showSuppressedMethod() {
    System.out.println("The number is: " + NUMBER);
  }

  public static void main(String[] args) {
    Example7 example = new Example7();
    example.showSuppressedMethod();
  }
}
// xdoc section -- end
