/*xml
<module name="Checker">
  <module name="RegexpSingleline">
    <property name="format" value="example"/>
  </module>
  <module name="SuppressionSingleFilter">
    <property name="files" value="Example3.java"/>
    <property name="checks" value="RegexpSinglelineCheck"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// filtered violation 9 lines above 'Line matches the illegal pattern'
// xdoc section -- start
public class Example3 {

  private int MyVariable = 5;
  // filtered violation below 'Line matches the illegal pattern'
  public void exampleMethod(int a, int b) {
    int value = 100;

    Integer. parseInt("3");
  }

  public void printExample() {
    int [] x;
    System.out.println(
            "example" // filtered violation 'Line matches the illegal pattern'
    );
  }
}
// xdoc section -- end
