/*xml
<module name="Checker">
  <module name="RegexpOnFilename">
    <property name="fileNamePattern" value="^([A-Z][a-z0-9]+\.?)+$"/>
    <property name="match" value="false"/>
    <property name="ignoreFileNameExtensions" value="true"/>
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
checks/regexp/regexponfilename/checkstyle.xml // violation, 'not camelcase'
// xdoc section -- end
*/
class Example5 {}
