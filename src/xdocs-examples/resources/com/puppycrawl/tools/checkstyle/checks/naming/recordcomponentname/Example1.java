/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RecordComponentName"/>
  </module>
</module>
*/
// Java17
package com.puppycrawl.tools.checkstyle.checks.naming.recordcomponentname;

// xdoc section - start
class Example1 {
  record Rec1(int other) {}

  record Rec2(String Values) {} // violation 'Name 'Values' must match pattern'

  record Rec3(double myNumber) {}
}
// xdoc section - end
