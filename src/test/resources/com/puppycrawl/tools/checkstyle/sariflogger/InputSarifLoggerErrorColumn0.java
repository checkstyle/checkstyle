/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.regexp.RegexpSinglelineCheck">
      <property name="format" value="System\.out\.println"/>
        <property name="message" value="Using System.out.println is not allowed.&#10;Please use a proper logging framework instead."/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.sariflogger;

public class InputSarifLoggerErrorColumn0 {
    public void methodWithSystemOut() {
        // violation below 'Using System.out.println is not allowed'
        System.out.println("This will trigger a violation with newline");
    }
}
