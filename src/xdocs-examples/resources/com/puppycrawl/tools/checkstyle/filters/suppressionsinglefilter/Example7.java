/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MagicNumber">
      <property name="id" value="customMagicNumber"/>
    </module>
  </module>
  <module name="SuppressionSingleFilter">
    <property name="id" value="customMagicNumber"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example7 {
  // filtered violation below ''5' is a magic number'
  private int MyVariable = 5;

  public void exampleMethod(int a, int b) {
    int value = 100; // filtered violation ''100' is a magic number'

    Integer. parseInt("3");
  }

  public void printExample() {
    int [] x;
    System.out.println(
            "example"
    );
  }
}
// xdoc section -- end
