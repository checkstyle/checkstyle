/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.MissingSwitchDefaultCheck"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.sariflogger;

public class InputSarifLoggerEscapedMessage {
    void test(int x) {
        switch (x) { // violation 'switch without "default" clause.'
            case 1:
                break;
        }
    }
}
