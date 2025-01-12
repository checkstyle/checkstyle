/*xml
<module name="Checker">
  <module name="RegexpOnFilename">
    <property name="fileNamePattern" value="Example2\.java"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.regexp.regexponfilename;

// xdoc section -- start
/*checks/regexp/regexponfilename/Example1.xml  // OK, .xml file allowed
/*checks/regexp/regexponfilename/Exampl1.java // Violation .java files not allowed */
public class Example2 { }
// xdoc section -- end
// violation 14 lines above 'File match folder pattern '' and file pattern'
