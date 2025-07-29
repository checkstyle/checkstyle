/*xml
<module name="Checker">
  <module name="SuppressionFilter">
    <property name="file" value="suppressionexample4.xml"/>
    <property name="optional" value="false"/>
  </module>
  <module name="TreeWalker">
    <module name="MemberName">
      <property name="format" value="^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"/>
    </module>
    <module name="ConstantName"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionfilter;
// xdoc section -- start
public class Example4 {

  public int log;
  // filtered violation above 'Name 'log' must match pattern'

  public int constant;
  // violation above 'Name 'constant' must match pattern'
}

class Inner {

  public static final int log = 10;
  // filtered violation above 'Name 'log' must match pattern'

  public static final String line = "This is a line";
  // violation above 'Name 'line' must match pattern'
}
// xdoc section -- end
