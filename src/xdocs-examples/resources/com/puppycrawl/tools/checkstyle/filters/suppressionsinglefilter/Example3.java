/*xml
<module name="Checker">
  <module name="RegexpSingleline">
    <property name="format" value="ex&#97;mple"/>
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
    System.out.println(
      "This is an example string." // filtered violation 'Line matches the illegal pattern 'example''
    );
  }

  public void noViolation() {
    System.out.println(
      "This string does not contain 'Example'."
    );
  }

}
// xdoc section -- end
