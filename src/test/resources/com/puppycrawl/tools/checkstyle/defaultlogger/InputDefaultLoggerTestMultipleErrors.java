/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.UnusedImportsCheck"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.defaultlogger;

import java.util.Arrays; // violation 'Unused import - java.util.Arrays.'
import java.util.List; // violation 'Unused import - java.util.List.'

public class InputDefaultLoggerTestMultipleErrors {
}
