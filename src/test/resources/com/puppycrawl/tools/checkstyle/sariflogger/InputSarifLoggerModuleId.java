/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.blocks.EmptyBlockCheck">
      <property name="id" value="strict"/>
    </module>
    <module name="com.puppycrawl.tools.checkstyle.checks.blocks.EmptyBlockCheck">
      <property name="id" value="lenient"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.sariflogger;

public class InputSarifLoggerModuleId {
    void method() {
        if (true) { } // 2 violations 'Must have at least one statement.'
        for (int i = 0; i < 10; i++) { } // 2 violations 'Must have at least one statement.'
    }
}
