/*xml
<module name="Checker">
  <module name="SuppressionFilter">
    <property name="file" value="suppressionexample2.xml"/>
    <property name="optional" value="false"/>
  </module>
  <module name="TreeWalker">
    <module name="EqualsAvoidNull">
      <property name="ignoreEqualsIgnoreCase" value="false"/>
      <property name="id" value="stringEqual"/>
    </module>
    <module name="LineLength">
      <property name="id" value="lineLength"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionfilter;
// xdoc section -- start
public class Example2 {

  // violation below, 'Line is longer than 80 characters'
  String line = "This line is long and exceeds the default limit of 80 characters.";

  public void foo() {

    String nullString = null;

    // filtered violation below 'expressions should be on the left side'
    nullString.equals("My_Sweet_String");
    "My_Sweet_String".equals(nullString);

    // filtered violation below 'expressions should be on the left side'
    nullString.equalsIgnoreCase("My_Sweet_String");
    "My_Sweet_String".equalsIgnoreCase(nullString);
  }
}
// xdoc section -- end
