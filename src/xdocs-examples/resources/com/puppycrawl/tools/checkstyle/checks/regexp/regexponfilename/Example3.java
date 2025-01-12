/*xml
<module name="Checker">
  <module name="RegexpOnFilename">
    <property name="fileNamePattern" value="TestExample"/>
    <property name="fileExtensions" value="java"/>
    <property name="match" value="false"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.regexp.regexponfilename;

// xdoc section -- start
/*checks/regexp/regexponfilename/TestExample.java
/*checks/regexp/regexponfilename/Example3.xml
/*checks/regexp/regexponfilename/Example3.java // Violation, 'pattern mismatch'*/
public class Example3 {}
// xdoc section -- end
// violation 17 lines above 'File not match folder pattern '' and file pattern'
