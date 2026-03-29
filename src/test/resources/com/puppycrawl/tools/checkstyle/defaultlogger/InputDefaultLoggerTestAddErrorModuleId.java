/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.UnusedImportsCheck">
        <property name="id" value="moduleId" />
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.defaultlogger;

import java.util.List; // violation 'Unused import - java.util.List.'

public class InputDefaultLoggerTestAddErrorModuleId {
}
