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
class UseCase1 {
  public UseCase1() {}
  // violation below 'Name 'UseCase1' must not equal the enclosing class name.'
  public void UseCase1() {}
}
// xdoc section -- end
