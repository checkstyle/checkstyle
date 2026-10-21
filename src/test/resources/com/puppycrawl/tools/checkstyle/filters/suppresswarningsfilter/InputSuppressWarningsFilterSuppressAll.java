/*xml
<module name="Checker">
  <module name="SuppressWarningsFilter"/>
  <module name="RegexpSingleline">
    <property name="format" value="forbidde[n]"/>
  </module>
  <module name="TreeWalker">
    <property name="skipFileOnJavaParseException" value="true"/>
    <module name="SuppressWarningsHolder"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppresswarningsfilter;

@SuppressWarnings("all")
public class InputSuppressWarningsFilterSuppressAll {
    // filtered violation below 'Line matches the illegal pattern'
    int forbidden;
}
