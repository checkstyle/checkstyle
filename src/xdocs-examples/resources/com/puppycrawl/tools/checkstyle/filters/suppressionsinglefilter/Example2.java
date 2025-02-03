/*xml
<module name="Checker">
  <module name="EqualsAvoidNull"/>
  <module name="JavadocMethod"/>
  <module name="SuppressionSingleFilter">
    <property name="id" value="stringEqual"/>
    <property name="files" value="Example2.java"/>
    <property name="checks" value="EqualsAvoidNull, JavadocMethod"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example2 {

  public void checkStringEquality(String str1, String str2) {
    // filtered violation ''.equals()' should be used for string comparison'
    assert str1 == str2 ;
  }
}
// xdoc section -- end
