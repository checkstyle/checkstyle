/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.blocks.EmptyBlockCheck"/>
    <module name="com.puppycrawl.tools.checkstyle.checks.blocks.EmptyCatchBlockCheck"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.sariflogger;

public class InputSarifLoggerMultipleRules {

    InputSarifLoggerMultipleRules() {
        for (int i = 0; i < 10; i++) { } // EmptyBlock violation

        try {
            // EmptyCatchBlock violation
        } catch (Exception e) {

        }
    }
}
