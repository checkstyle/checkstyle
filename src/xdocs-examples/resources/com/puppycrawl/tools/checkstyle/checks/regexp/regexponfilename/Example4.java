/*xml
<module name="Checker">
  <module name="RegexpOnFilename">
    <property name="folderPattern"
      value="[\\/]src[\\/]\w+[\\/]resources[\\/]"/>
    <property name="match" value="false"/>
    <property name="fileExtensions" value="properties, xml"/>
    <message key="regexp.filename.mismatch"
      value="pattern mismatch"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.regexp.regexponfilename;
/*
// xdoc section -- start
.../README.md
.../TestExample.java
.../TestExample.md
.../TestExample.xml // violation, 'pattern mismatch'
.../Test Example.xml // violation, 'contains whitespace'
.../checkstyle.xml
// xdoc section -- end
*/
class Example4 {}
