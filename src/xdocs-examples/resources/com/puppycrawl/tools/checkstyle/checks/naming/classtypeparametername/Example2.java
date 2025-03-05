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
  class MyClass1<T> {}        // violation, Name 'T' must match pattern '^[A-Z]{2,}$
  class MyClass2<t> {}        // violation, Name 't' must match pattern '^[A-Z]{2,}$
  class MyClass3<abc> {}      // violation,  Name 'abc' must match pattern '^[A-Z]{2,}
  class MyClass4<LISTENER> {}
  class MyClass5<RequestT> {} // violation, Name 'RequestT' must match pattern '^[A-Z]{2,}$'
}
// xdoc section -- end
