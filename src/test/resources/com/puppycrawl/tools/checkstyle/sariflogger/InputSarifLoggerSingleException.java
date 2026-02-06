/*xml
<module name="Checker">
  <module name="TreeWalker">
    <!-- This check does NOT exist and will cause ClassNotFoundException -->
    <module name="com.puppycrawl.tools.checkstyle.checks.fake.NonExistentCheck"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.sariflogger;

public class InputSarifLoggerSingleException {
    int x = 1;
}
