/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.naming.MethodNameCheck"/>
    <module name="com.puppycrawl.tools.checkstyle.checks.naming.MethodNameCheck">
        <property name="id" value="twCheck"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.xmllogger;

public class InputXMLLoggerErrorWithAndWithoutModuleId {

    public void Method() {}
}


