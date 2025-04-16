/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocStyle"/>
    <module name="MagicNumber"/>
  </module>
  <module name="SuppressionSingleFilter">
    <property name="checks" value="JavadocStyle|MagicNumber"/>
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
  public void exampleMethod() {
    int value = 100; // filtered violation ''100' is a magic number'
  }
}
// xdoc section -- end
