/*xml
<module name="Checker">
  <module name="RegexpOnFilename">
    <property name="fileNamePattern" value=".*\.(properties|xml)$"/>
    <property name="match" value="false"/>
    <message key="regexp.filename.mismatch"
      value="Only property and xml files to be located in the resource folder"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.regexp.regexponfilename;
/*
// xdoc section -- start
.../checkstyle.xml
.../Test Example1.xml
.../TestExample2.xml
.../TestExample3.md  // violation, 'Only property and xml files to be located in the resource folder'
.../TestExample4.xml
// xdoc section -- end
*/
class Example4 {}
