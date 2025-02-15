/*xml
<module name="Checker">
  <module name="NoWhitespaceAfter"/>
  <module name="SuppressionSingleFilter">
    <property name="files" value="Example4.java"/>
    <property name="checks" value="NoWhitespaceAfter"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example4 {

  // filtered violation 'WhiteSpace after ',''
  public void exampleMethod(int a, int b) {
  }

  public void exampleMethod2() {
    int x = 5 ; // filtered violation 'WhiteSpace before ';''
  }

}
// xdoc section -- end
