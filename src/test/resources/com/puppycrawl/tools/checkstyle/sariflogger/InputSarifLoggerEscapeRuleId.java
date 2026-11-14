/*xml
<module name="Checker">
  <module name="com.puppycrawl.tools.checkstyle.checks.regexp.RegexpSinglelineCheck">
    <property name="id" value="line&quot;only\id/slash"/>
    <property name="format" value="MARK[E]R"/>
  </module>
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.blocks.EmptyBlockCheck">
      <property name="id" value="with&quot;column\id/slash"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.sariflogger;

public class InputSarifLoggerEscapeRuleId {
    int MARKER; // violation
    void method() {
        if (true) { } // violation
    }
}
