/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.IllegalTokenTextCheck">
      <property name="tokens" value="NUM_INT"/>
      <property name="format" value="1"/>
      <property name="message" value="\n"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.sariflogger;

public class InputSarifLoggerEscapeNewline {
    int x = 1;
}
