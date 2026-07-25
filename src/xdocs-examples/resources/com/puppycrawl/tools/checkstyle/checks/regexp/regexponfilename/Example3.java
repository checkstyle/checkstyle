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
// xdoc section -- start
/*
.../checkstyle.xml
.../Test Example1.xml
.../TestExample2.xml
.../TestExample3.md  // violation 'No *.md files other than README.md'
.../TestExample4.xml
.../Example1.java
*/
class Example3{}
// xdoc section -- end
