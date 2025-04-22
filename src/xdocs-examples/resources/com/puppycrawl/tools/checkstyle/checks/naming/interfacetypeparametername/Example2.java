/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="InterfaceTypeParameterName">
       <property name="format" value="^[a-zA-Z]$"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.interfacetypeparametername;

// xdoc section -- start
class Example2 {
  interface FirstInterface<T> {}
  interface SecondInterface<t> {}
  interface ThirdInterface<type> {} // violation 'Name 'type' must match pattern'
}
// xdoc section -- end
