/*xml
<module name="Checker">
  <module
      name="com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck">
     <property name="severity" value="ignore"/>
     <property name="max" value="50"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.xmllogger;

public class InputXMLLoggerEmpty {
    String longLine = "This line is definitely more than Fifty characters long.";
}
