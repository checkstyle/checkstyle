/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.whitespace.ParenPadCheck">
      <property name="option" value="nospace"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.sariflogger;

public class InputSarifLoggerErrorColumn0 {
    // violation below: '(' is followed by whitespace and ')' is preceded with whitespace
    void method( ) { }
}
