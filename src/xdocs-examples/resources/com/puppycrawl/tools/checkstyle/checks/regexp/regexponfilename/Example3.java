/*xml
<module name="Checker">
  <module name="RegexpOnFilename">
    <property name="fileNamePattern" value="README"/>
    <property name="fileExtensions" value="md"/>
    <property name="match" value="false"/>
    <message key="regexp.filename.mismatch"
      value="No *.md files other then README.md"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.regexp.regexponfilename;
/*
// xdoc section -- start
.../README.md
.../TestExample.java
.../TestExample.md // violation, 'only README.md allowed'
.../TestExample.xml
.../Test Example.xml // violation, 'contains whitespace'
.../checkstyle.xml
// xdoc section -- end
*/
class Example3{}
