/*xml
<module name="Checker">
  <module name="RegexpOnFilename">
    <property name="fileNamePattern" value="README"/>
    <property name="fileExtensions" value="md"/>
    <property name="match" value="false"/>
    <message key="regexp.filename.mismatch"
      value="No *.md files other than README.md"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.regexp.regexponfilename;
/*
// xdoc section -- start
.../checkstyle.xml
.../Test Example1.xml
.../TestExample2.xml
.../TestExample3.md  // violation, 'No *.md files other than README.md'
.../TestExample4.xml
// xdoc section -- end
*/
class Example3{}
