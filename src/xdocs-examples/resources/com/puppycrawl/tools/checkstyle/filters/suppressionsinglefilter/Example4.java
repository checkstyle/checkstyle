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

  // filtered violation 'WhiteSpace after ',''
  public void exampleMethod(int a, int b) {
    System.out.println(
      "This is an example."
    );
  }

  public void exampleMethod2() {
    int x = 5 ; // filtered violation 'WhiteSpace before ';''
  }

  // No violation expected here
  public void exampleMethod3() {
    int x = 5;
  }

  public static void main(String[] args) {
    Example4 example = new Example4();
    example.exampleMethod(1, 2);
    example.exampleMethod2();
    example.exampleMethod3();
  }

}
// xdoc section -- end
