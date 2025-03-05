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
  class MyClass2<t> {}        // violation,  Name 't' must match pattern '^[A-Z]$'
  class MyClass3<abc> {}      // violation, Name 'abc' must match pattern '^[A-Z]$'
  class MyClass4<LISTENER> {} // violation, Name 'LISTENER' must match pattern '^[A-Z]$'
  class MyClass5<RequestT> {} // violation, Name 'RequestT' must match pattern '^[A-Z]$'
}
// xdoc section -- end
