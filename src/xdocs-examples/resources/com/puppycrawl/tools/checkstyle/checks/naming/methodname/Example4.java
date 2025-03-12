/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodName">
       <property name="format" value="^[a-zA-Z](_?[a-zA-Z0-9]+)*$"/>
       <property name="allowClassName" value="false"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.methodname;

// xdoc section -- start
class Example4 {
  public Example4() {}
  public void Example4() {} // violation 'Example4' must match the pattern
}
// xdoc section -- end
