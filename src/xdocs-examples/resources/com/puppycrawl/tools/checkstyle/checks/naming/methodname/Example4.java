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
  // violation below 'Name 'Example4' must not equal the enclosing class name.'
  public void Example4() {}
}
// xdoc section -- end
