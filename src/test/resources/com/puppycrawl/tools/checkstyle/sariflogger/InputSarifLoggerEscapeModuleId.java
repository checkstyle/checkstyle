/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.blocks.EmptyBlockCheck">
      <property name="id" value="my&quot;id\with/slash"/>
    </module>
    <module name="RegexpSingleline">
      <property name="id" value="regexp&quot;id"/>
      <property name="format" value="// This code is copyrighted\."/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.sariflogger;
// This code is copyrighted.
public class InputSarifLoggerEscapeModuleId {
    void method() {
        if (true) { } // violation 'Must have at least one statement.'
    }
}
