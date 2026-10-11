/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.UnusedImportsCheck"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.sariflogger;

import java.util.List; // violation 'Unused import - java.util.List.'

public class InputSarifLoggerSingleError {
}
