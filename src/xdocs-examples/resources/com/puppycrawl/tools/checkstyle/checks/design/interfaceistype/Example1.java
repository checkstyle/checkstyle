/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="InterfaceIsType"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.design.interfaceistype;

// xdoc section -- start
class Example1 {
  // violation below, 'interfaces should describe a type and hence have methods.'
  interface Test1 {
    int a = 3;
  }

  // ok below, because marker interfaces are allowed by default.
  interface Test2 {

  }

  interface Test3 { // ok, because it has a method.
    int a = 3;
    void test();
  }
}
// xdoc section -- end
