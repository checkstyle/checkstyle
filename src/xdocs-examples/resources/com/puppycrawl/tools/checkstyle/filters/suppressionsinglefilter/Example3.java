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
// filtered violation 10 lines above 'Line matches the illegal pattern'
// xdoc section -- start
public class Example3 {

  public void printExample() {
    System.out.println(
      "example" // filtered violation 'Line matches the illegal pattern 'example''
    );
  }

  public void noViolation() {
    System.out.println(
      "RegexpSingleline is case sensitive by default. 'Example' in not matching."
    );
  }

}
// xdoc section -- end
