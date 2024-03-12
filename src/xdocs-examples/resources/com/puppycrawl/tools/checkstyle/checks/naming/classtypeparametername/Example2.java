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
  class MyClass1<T> {}        // violation
  class MyClass2<t> {}        // violation
  class MyClass3<abc> {}      // violation
  class MyClass4<LISTENER> {}
  class MyClass5<RequestT> {} // violation
}
// xdoc section -- end
