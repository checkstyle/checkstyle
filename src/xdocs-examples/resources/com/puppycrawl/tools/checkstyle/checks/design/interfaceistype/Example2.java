/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="InterfaceIsType">
      <property name="allowMarkerInterfaces" value="false"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.design.interfaceistype;

// xdoc section -- start
class Example2 {
  // violation below, 'interfaces should describe a type and hence have methods.'
  interface Test1 {
    int a = 3;
  }

  // violation below, 'interfaces should describe a type and hence have methods.'
  interface Test2 {

  }

  interface Test3 { // ok, because it has a method.
    int a = 3;
    void test();
  }
}
// xdoc section -- end
