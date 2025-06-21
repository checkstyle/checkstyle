/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WhitespaceAround">
      <property name="id" value="whitespaceCheck"/>
    </module>
    <module name="MethodLength">
      <property name="id" value="methodLengthCheck"/>
      <property name="max" value="2"/>
    </module>
  </module>
  <module name="SuppressionSingleFilter">
    <property name="files" value="Example11.java"/>
    <property name="id" value="whitespaceCheck"/>
    <property name="columns" value="12-30"/>
    <property name="lines" value="17"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example11 {
  // This method demonstrates the combined usage of id and columns properties
  public void exampleMethod() {
    int x=5; // filtered violation, WhitespaceAround check will be suppressed by id and columns
    
    // MethodLength violation is not filtered since we only filter the whitespaceCheck id
    int y = 10;
    int z = 15;
  }
}
// xdoc section -- end 