/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RecordTypeParameterName">
      <property name="format" value="^[a-zA-Z]$"/>
    </module>
  </module>
</module>
*/
// Java17
package com.puppycrawl.tools.checkstyle.checks.naming.recordtypeparametername;

// xdoc section -- start
class Example2 {
  record Record1<T>() {}

  record Record2<t>() {}

  record Record3<abc>() {} // violation, Name "abc" must match pattern '^[A-Z]$'
}
// xdoc section -- end
