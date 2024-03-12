/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ClassTypeParameterName"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.classtypeparametername;

// xdoc section -- start
class Example1 {
  class MyClass1<T> {}
  class MyClass2<t> {}        // violation
  class MyClass3<abc> {}      // violation
  class MyClass4<LISTENER> {} // violation
  class MyClass5<RequestT> {} // violation
}
// xdoc section -- end
