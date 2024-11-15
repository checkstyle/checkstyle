/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RecordTypeParameterName">
      <property name="format" value="^[a-zA-Z]$"/>
    </module>
  </module>
</module>
*/
//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.naming.recordtypeparametername;

// xdoc section -- start
class Example2 {
  record MyRecord1<T>() {}

  record MyRecord2<t>() {}

  record MyRecord3<abc>() {} // violation,
  // the record type parameter name should match the regular expression "^[a-zA-Z]$"
}
// xdoc section -- end
