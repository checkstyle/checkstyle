/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RegexpSingleline">
      <property name="format" value="SINGLE_EXCEPTION_TRIGGER"/>
      <property name="message" value="found an error"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.sariflogger;
// comment with SINGLE_EXCEPTION_TRIGGER
// violation above 'found an error'
public class InputSarifLoggerSingleException {

}
