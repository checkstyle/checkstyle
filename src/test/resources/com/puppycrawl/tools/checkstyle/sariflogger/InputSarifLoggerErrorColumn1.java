/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.regexp.RegexpSinglelineCheck">
      <property name="format" value="^\s+System\.out\.println"/>
      <property name="message" value="Using System.out.println is not allowed.&#10;Please use a proper logging framework instead."/>
    </module>
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.UnusedImportsCheck"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.sariflogger;

import java.util.List; // violation 'Unused import - java.util.List.'

public class InputSarifLoggerErrorColumn1 {
    public void methodWithSystemOut() {
        // violation below 'Using System.out.println is not allowed'
        System.out.println("This will trigger a violation with newline");
    }
}
