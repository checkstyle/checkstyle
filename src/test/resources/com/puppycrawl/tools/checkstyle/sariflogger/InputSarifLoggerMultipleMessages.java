/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ModifierOrder"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.sariflogger;

public class InputSarifLoggerMultipleMessages {
    // violation 'mod.order' - 'static' should come before 'final'
    final static int FIELD1 = 1;

    // violation 'annotation.order' - annotation should come before modifiers
    private @Deprecated void method1() {
    }
}
