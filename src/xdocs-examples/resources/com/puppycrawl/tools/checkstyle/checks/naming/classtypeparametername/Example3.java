/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ClassTypeParameterName">
      <property name="format" value="(^[A-Z][0-9]?)$|([A-Z][a-zA-Z0-9]*[T]$)"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.classtypeparametername;

// xdoc section -- start
class Example3 {
  class MyClass1<T> {}
  class MyClass2<t> {}        // violation
  class MyClass3<abc> {}      // violation
  class MyClass4<LISTENER> {} // violation
  class MyClass5<RequestT> {}
}
// xdoc section -- end
