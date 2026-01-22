/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.IllegalTokenTextCheck">
      <property name="tokens" value="NUM_INT"/>
      <property name="format" value="^111$"/>
      <property name="severity" value="error"/>
      <property name="message" value="Error level violation"/>
    </module>
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.IllegalTokenTextCheck">
      <property name="tokens" value="NUM_INT"/>
      <property name="format" value="^222$"/>
      <property name="severity" value="warning"/>
      <property name="message" value="Warning level violation"/>
    </module>
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.IllegalTokenTextCheck">
      <property name="tokens" value="NUM_INT"/>
      <property name="format" value="^333$"/>
      <property name="severity" value="info"/>
      <property name="message" value="Info level violation"/>
    </module>
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.IllegalTokenTextCheck">
      <property name="tokens" value="NUM_INT"/>
      <property name="format" value="^444$"/>
      <property name="severity" value="ignore"/>
      <property name="message" value="Ignore level violation"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.sariflogger;

public class InputSarifLoggerAllSeverityLevels {

    int error = 111;
    int warning = 222;
    int info = 333;
    int ignore = 444;
}

