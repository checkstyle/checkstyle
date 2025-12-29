/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.MagicNumberCheck"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.sariflogger;

public class InputSarifLoggerUnexpectedSeverity {
    void test() {
        int x = 1; // violation 'magic number'
    }
}
