/*xml
<module name="Checker">
  <module name="SuppressionSingleFilter">
    <property name="checks" value="JavadocStyleCheck"/>
    <property name="files" value="Example1.java"/>
    <property name="lines" value="1,5-100"/>
  </module>
  <module name="SuppressionSingleFilter">
    <property name="checks" value="MagicNumberCheck"/>
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

  // This method is missing a Javadoc comment
  // which should trigger JavadocStyleCheck violation
  public void exampleMethod() {
    int value = 100;
    // This should trigger MagicNumberCheck violation, as 100 is a magic number
    System.out.println(value);
  }
}
// xdoc section -- end
