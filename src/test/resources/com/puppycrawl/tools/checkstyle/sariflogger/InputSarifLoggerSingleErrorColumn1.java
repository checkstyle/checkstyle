/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.IllegalTokenTextCheck">
      <property name="tokens" value="IDENT"/>
      <property name="format" value="^^String$"/>
      <property name="message" value="Avoid using String. \n try again"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.sariflogger;

public class InputSarifLoggerSingleErrorColumn1 {

String str = "test";
}
