/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NoWhitespaceAfter"/>
    <module name="MagicNumber"/>
  </module>
  <module name="SuppressionSingleFilter">
    <property name="checks" value="NoWhitespaceAfter|MagicNumber"/>
    <property name="files" value="Example1.java"/>
    <property name="lines" value="1,5-100"/>
  </module>
  <module name="SuppressionSingleFilter">
    <property name="message" value="Missing a Javadoc comment"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example1 {
  // filtered violation below ''5' is a magic number'
  private int MyVariable = 5;

  public void exampleMethod(int a, int b) {
    int value = 100; // filtered violation ''100' is a magic number'

    Integer. parseInt("3"); // filtered violation ''.' is followed by whitespace'
  }

  public void printExample() {
    int [] x; // filtered violation ''int' is followed by whitespace'
    System.out.println(
            "example"
    );
  }
}
// xdoc section -- end
