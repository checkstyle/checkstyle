/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.IllegalTokenTextCheck">
      <property name="tokens" value="NUM_INT"/>
      <property name="format" value="^123$"/>
      <property name="severity" value="warning"/>
      <property name="message" value="Avoid using the number 123. \n try again"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.sariflogger;

public class InputSarifLoggerSingleWarning {

    int x = 123;
}
