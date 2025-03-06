/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ClassTypeParameterName">
      <property name="format" value="^[A-Z]{2,}$"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.classtypeparametername;

// xdoc section -- start
class Example2 {
  class MyClass1<T> {}        // violation 'Name 'T' must match pattern'
  class MyClass2<t> {}        // violation 'Name 't' must match pattern'
  class MyClass3<abc> {}      // violation 'Name 'abc' must match pattern'
  class MyClass4<LISTENER> {}
  class MyClass5<RequestT> {} // violation 'Name 'RequestT' must match pattern'
}
// xdoc section -- end
