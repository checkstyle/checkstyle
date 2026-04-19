/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.IllegalTokenTextCheck">
      <property name="tokens" value="NUM_INT"/>
      <property name="format" value="1"/>
      <property name="message" value="&quot;\\ \n"/>
    </module>
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.IllegalTokenTextCheck">
      <property name="tokens" value="NUM_INT"/>
      <property name="format" value="1"/>
      <property name="message" value="&#9;"/>
    </module>
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.IllegalTokenTextCheck">
      <property name="tokens" value="NUM_INT"/>
      <property name="format" value="1"/>
      <property name="message" value="&#xD;"/>
    </module>
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.IllegalTokenTextCheck">
      <property name="tokens" value="STRING_LITERAL"/>
      <property name="format" value="/"/>
    </module>
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.IllegalTokenTextCheck">
      <property name="tokens" value="STRING_LITERAL"/>
      <property name="format" value=" "/>
    </module>
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.IllegalTokenTextCheck">
      <property name="tokens" value="STRING_LITERAL"/>
      <property name="format" value="bar1234"/>
    </module>
  </module>
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.IllegalTokenCheck">
      <property name="tokens" value="STRING_LITERAL"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.sariflogger;
public class InputSarifLoggerEscapeAll {
    int x = 1; // violation '\t'
    String slash = "/"; // violation
    String space = " "; // violation
    String bar1234 = "bar1234"; // violation
    String contains_u0008 = ""; // violation 'Using ".*" is not allowed.'
    String contains_u000C = ""; // violation 'Using ".*" is not allowed.'
    String contains_u0010 = ""; // violation 'Using ".*" is not allowed.'
    String contains_u001E = ""; // violation 'Using ".*" is not allowed.'
    String contains_u001F = ""; // violation 'Using ".*" is not allowed.'
}
