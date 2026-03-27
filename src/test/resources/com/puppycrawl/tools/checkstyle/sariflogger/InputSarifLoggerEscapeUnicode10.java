/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.IllegalTokenTextCheck">
      <property name="tokens" value="NUM_INT"/>
      <property name="format" value="1"/>
      <property name="message" value="\u0010"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.sariflogger;

public class InputSarifLoggerEscapeUnicode10 {
    int x = 1;
}
