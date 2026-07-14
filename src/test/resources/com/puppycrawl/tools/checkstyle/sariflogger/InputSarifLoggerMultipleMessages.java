/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ModifierOrder"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.sariflogger;

public class InputSarifLoggerMultipleMessages {
    // violation below 'static' should come before 'final'
    final static int FIELD1 = 1;

    // violation below 'annotation should come before modifiers'
    private @Deprecated void method1() {
    }
}
