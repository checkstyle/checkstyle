/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RecordTypeParameterName"/>
  </module>
</module>
*/
//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.naming.recordtypeparametername;

// xdoc section -- start
class Example1 {
  record MyRecord1<T>() {}

  record MyRecord2<t>() {} // violation,
  // the record type parameter name should match the regular expression "^[A-Z]$"
  record MyRecord3<abc>() {} // violation,
  // the record type parameter name should match the regular expression "^[A-Z]$"
}
// xdoc section -- end
