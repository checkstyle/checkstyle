/*xml
<module name="Checker">
  <module name="RegexpSingleline">
    <property name="format" value=".*example.*"/>
  </module>
  <module name="SuppressionSingleFilter">
    <property name="files" value="Example3.java"/>
    <property name="checks" value="RegexpSinglelineCheck"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example3 {

  public void printExample() {
    // This line will trigger the RegexpSinglelineCheck violation due to "example"
    System.out.println(
      "This is an example string."
    );
  }

  public void noViolation() {
    // This line will not trigger any violation
    System.out.println(
      "This string does not contain 'example'."
    );
  }

  public static void main(String[] args) {
    Example3 example = new Example3();
    example.printExample();  // This line triggers the violation
    example.noViolation();   // No violation here
  }

}
// xdoc section -- end
