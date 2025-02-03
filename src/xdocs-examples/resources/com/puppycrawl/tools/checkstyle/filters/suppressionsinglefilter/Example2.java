/*xml
<module name="Checker">
  <module name="SuppressionSingleFilter">
    <property name="id" value="stringEqual"/>
    <property name="files" value="SomeTestCode.java"/>
  </module>
  <module name="TreeWalker">
    <module name="StringLiteralEquality"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example2 {
  // Checkstyle: Suppress stringEqual check for this line
  public boolean checkStrings(String a, String b) {
    return a == b; // Normally flagged by Checkstyle (stringEqual)
  }
}
// xdoc section -- end
