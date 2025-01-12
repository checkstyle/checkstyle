/*xml
<module name="Checker">
  <module name="RegexpOnFilename">
    <property name="folderPattern"
      value="[\\/]src[\\/]\w+[\\/]resources[\\/]"/>
    <property name="match" value="false"/>
    <property name="fileExtensions" value="properties, xml"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.regexp.regexponfilename;
/*
// xdoc section -- start
checks/regexp/regexponfilename/README.md
checks/regexp/regexponfilename/TestExample.java
checks/regexp/regexponfilename/TestExample.md
checks/regexp/regexponfilename/TestExample.xml // violation, 'pattern mismatch'
checks/regexp/regexponfilename/Test Example.xml // violation, 'contains whitespace'
checks/regexp/regexponfilename/checkstyle.xml
// xdoc section -- end
*/
class Example4 {}
