/*xml
<module name="Checker">
  <module name="RegexpOnFilename">
    <property name="fileNamePattern" value="TestExample2\.xml"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.regexp.regexponfilename;
/*
// xdoc section -- start
checks/regexp/regexponfilename/Example2.java  // OK, .java file allowed
checks/regexp/regexponfilename/TestExample2.xml // Violation .xml files not allowed
*/
public class Example2 { }
// xdoc section -- end
