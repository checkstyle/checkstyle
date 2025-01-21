/*xml
<module name="Checker">
  <module name="RegexpOnFilename">
    <property name="fileNamePattern" value="README"/>
    <property name="fileExtensions" value="md"/>
    <property name="match" value="false"/>
    <message key="regexp.filename.mismatch"
      value="No '*.md' files other then 'README.md'"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.regexp.regexponfilename;
/*
// xdoc section -- start
checks/regexp/regexponfilename/README.md
checks/regexp/regexponfilename/TestExample.java
checks/regexp/regexponfilename/TestExample.md // violation, 'only README.md allowed'
checks/regexp/regexponfilename/TestExample.xml
checks/regexp/regexponfilename/Test Example.xml // violation, 'contains whitespace'
checks/regexp/regexponfilename/checkstyle.xml
// xdoc section -- end
*/
class Example3{}
