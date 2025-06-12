/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RecordTypeParameterName"/>
  </module>
</module>
*/
// Java17
package com.puppycrawl.tools.checkstyle.checks.naming.recordtypeparametername;

// xdoc section -- start
class Example1 {
  record Record1<T>() {}

  record Record2<t>() {} // violation, Name "t" must match pattern '^[A-Z]$'

  record Record3<abc>() {} // violation, Name "abc" must match pattern '^[A-Z]$'
}
// xdoc section -- end
