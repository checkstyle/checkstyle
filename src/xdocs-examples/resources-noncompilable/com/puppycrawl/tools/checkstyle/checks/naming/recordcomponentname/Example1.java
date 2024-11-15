/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RecordComponentName"/>
  </module>
</module>
*/
//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.naming.recordcomponentname;

// xdoc section -- start
class Example1 {
  record MyRecord1(String value, int otherComponentName) {}

  record MyRecord2(String... Values) {} // violation,
  //the record component name should match the regex "^[a-z][a-zA-Z0-9]*$"
  record MyRecord3(double my_number) {} // violation,
  //the record component name should match the regex "^[a-z][a-zA-Z0-9]*$"
}
// xdoc section -- end
