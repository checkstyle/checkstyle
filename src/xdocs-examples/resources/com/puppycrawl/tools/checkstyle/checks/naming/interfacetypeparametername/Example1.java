/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="InterfaceTypeParameterName"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.interfacetypeparametername;

// xdoc section -- start
class Example1 {
  interface FirstInterface<T> {}
  interface SecondInterface<t> {} // violation 'Name 't' must match pattern'
}
// xdoc section -- end
