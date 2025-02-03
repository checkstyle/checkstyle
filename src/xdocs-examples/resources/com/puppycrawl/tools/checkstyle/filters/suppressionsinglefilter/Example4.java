/*xml
<module name="Checker">
  <module name="SuppressionSingleFilter">
    <property name="files" value=".*\.java"/>
    <property name="checks" value=".*"/>
  </module>
  <module name="TreeWalker">
    <module name="MethodName"/>
    <module name="NeedBraces"/>
    <module name="MagicNumber"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example4 {

  // Checkstyle: Suppress MethodNameCheck for this line
  public void METHOD_IN_UPPERCASE() { // Suppressed MethodNameCheck here
    System.out.println("This method name violates naming conventions.");
  }

  // Checkstyle: Suppress NeedBracesCheck for this line
  public void checkNumber(int value) {
    // Suppressed NeedBracesCheck here
    if (value > 0) System.out.println("Positive number");
  }
}
// xdoc section -- end
