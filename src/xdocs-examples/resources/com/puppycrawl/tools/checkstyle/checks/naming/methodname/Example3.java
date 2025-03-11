/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodName">
       <property name="format" value="^[a-zA-Z](_?[a-zA-Z0-9]+)*$"/>
       <property name="allowClassName" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.methodname;

// xdoc section -- start
class Example3 {
  public Example3() {}
  public void Example3() {} // violation 'Example3' must match the pattern
}
// xdoc section -- end
