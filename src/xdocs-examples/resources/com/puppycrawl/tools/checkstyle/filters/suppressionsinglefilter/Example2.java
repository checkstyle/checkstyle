/*xml
<module name="Checker">
  <module name="SuppressionSingleFilter">
    <property name="id" value="stringEqual"/>
    <property name="files" value="Example2.java"/>
    <property name="checks" value="EqualsAvoidNull, JavadocMethod"/>
  </module>
  <module name="com.puppycrawl.tools.checkstyle.checks.coding.EqualsAvoidNullCheck"/>
  <module name="com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck"/>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example2 {

  public void checkStringEquality(String str1, String str2) {
    // filtered violation ''.equals()' should be used for string comparison'
    if (str1 == str2) {
      System.out.println("Strings are equal!");
    }
  }
}
// xdoc section -- end
