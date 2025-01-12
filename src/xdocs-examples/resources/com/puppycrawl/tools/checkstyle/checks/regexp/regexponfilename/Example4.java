/*xml
<module name="Checker">
  <module name="RegexpOnFilename">
    <property name="folderPattern"
      value="[\\/]src[\\/]\w+[\\/]resources[\\/]"/>
    <property name="match" value="false"/>
    <property name="fileExtensions" value="java"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.regexp.regexponfilename;

// xdoc section -- start
/*checks/regexp/regexponfilename/TestExample.xml
/*checks/regexp/regexponfilename/Example4.java // Violation, 'pattern mismatch'*/
public class Example4 { }
// xdoc section -- end
// violation 17 lines above 'File not match folder pattern'
