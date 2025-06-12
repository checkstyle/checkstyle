/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RecordComponentName">
        <property name="format" value="^[a-z]+$"/>
    </module>
  </module>
</module>
*/
// Java17
package com.puppycrawl.tools.checkstyle.checks.naming.recordcomponentname;

// xdoc section -- start
class Example2 {
  record Rec1(int other) {}

  record Rec2(String Values) {} // violation, Name must match '^[a-z]+$'

  record Rec3(double myNumber) {} // violation, Name must match '^[a-z]+$'
}
// xdoc section -- end
