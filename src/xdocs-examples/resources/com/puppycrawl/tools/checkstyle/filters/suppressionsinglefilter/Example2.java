/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NoWhitespaceAfter"/>
  </module>
  <module name="SuppressionSingleFilter">
    <property name="files" value="Example2.java"/>
    <property name="checks" value="NoWhitespaceAfter"/>
    <property name="lines" value="18"/>
    <property name="columns" value="12"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example2 {

  public void exampleMethod(int a, int b) {
    // filtered violation below ''.' is followed by whitespace'
    Integer. parseInt("3");
  }

  public void exampleMethod2() {
    int [] x; // violation ''int' is followed by whitespace'
  }

}
// xdoc section -- end
