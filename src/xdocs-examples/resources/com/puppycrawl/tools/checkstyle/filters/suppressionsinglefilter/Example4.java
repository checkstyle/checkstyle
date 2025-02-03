/*xml
<module name="Checker">
  <module name="SuppressionSingleFilter">
    <property name="files" value="Example4.java"/>
    <property name="checks" value="NoWhitespaceAfter"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example4 {

  // Normally trigger NoWhitespaceAfter violation due to extra space after ','
  public void exampleMethod(int a, int b) {
    System.out.println(
      "This is an example."
    );
  }

  // Normally trigger NoWhitespaceAfter violation due to extra space after ';'
  public void exampleMethod2() {
    int x = 5; // Fixed: Removed extra space before semicolon
  }

  // No violation expected here
  public void exampleMethod3() {
    int x = 5; // Proper usage
  }

  public static void main(String[] args) {
    Example4 example = new Example4();
    example.exampleMethod(1, 2);
    example.exampleMethod2();
    example.exampleMethod3();
  }

}
// xdoc section -- end
