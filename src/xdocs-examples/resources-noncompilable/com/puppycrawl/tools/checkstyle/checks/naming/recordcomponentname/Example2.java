/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RecordComponentName">
        <property name="format" value="^[a-z]+$"/>
    </module>
  </module>
</module>
*/
//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.naming.recordcomponentname;

// xdoc section -- start
class Example2 {
  record MyRecord1(String value, int other) {}

  record MyRecord2(String... strings) {}

  record MyRecord3(double myNumber) {} // violation,
  //the record component name should match the regex "^[a-z]+$"
}
// xdoc section -- end
