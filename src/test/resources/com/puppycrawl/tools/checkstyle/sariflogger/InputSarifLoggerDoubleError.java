/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.UnusedImportsCheck">
        <property name="severity" value="info"/>
    </module>
    <module name="com.puppycrawl.tools.checkstyle.checks.design.VisibilityModifierCheck">
        <property name="severity" value="ignore"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.sariflogger;

import java.util.List; // violation 'Unused import - java.util.List.'

public class InputSarifLoggerDoubleError {
    int field1; // violation, must have a visibility modifier 'must be private'
}
