/*xml
<module name="Checker">
  <module name="SuppressionSingleFilter">
    <property name="files" value="[/\\]target[/\\]"/>
    <property name="checks" value=".*"/>
  </module>
  <module name="MethodNameCheck"/>
  <module name="NeedBracesCheck"/>
  <module name="MagicNumberCheck"/>
  <module name="FinalClassCheck"/>
  <module name="JavadocMethodCheck"/>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example4 {
  // violation below, 'Method name should be in camelCase (normally flagged)'
  public void METHOD_IN_UPPERCASE() {
    System.out.println("This method name violates naming conventions.");
  }

  // violation below, 'No braces for single-line if statement (normally flagged)'
  public void checkNumber(int value) {
    if (value > 0) System.out.println("Positive number");
  }
}
// xdoc section -- end
