/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RegexpSingleline">
      <property name="format" value="// This code is copyrighted\."/>
      <property name="message" value="Copyright comment found. \n try again"/>
    </module>
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.UnusedImportsCheck"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.sariflogger;

import java.util.List; // violation 'Unused import - java.util.List.'
// This code is copyrighted.
public class InputSarifLoggerErrorColumn1 {
}
