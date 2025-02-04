/*xml
<module name="Checker">
  <module name="SuppressionSingleFilter">
    <property name="id" value="stringEqual"/>
    <property name="files" value="SomeTestCode.java"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example2 {
  public boolean checkStrings(String a, String b) {
    // Violation below, 'Use equals() for string comparison instead of '==''
    return a == b; // Normally flagged by Checkstyle (stringEqual)
  }
}
// xdoc section -- end
