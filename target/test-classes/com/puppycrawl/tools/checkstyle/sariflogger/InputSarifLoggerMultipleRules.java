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
        for (int i = 0; i < 10; i++) { } // violation 'Must have at least one statement.'

        try { // violation 'Must have at least one statement.'

        } catch (Exception e) {
        } // violation above 'Empty catch block.'
    }
}
