/*xml
<module name="Checker">
  <module name="RegexpOnFilename">
    <property name="fileNamePattern" value="^([A-Z][a-z0-9]+\.?)+$"/>
    <property name="match" value="false"/>
    <property name="ignoreFileNameExtensions" value="true"/>
    <message key="regexp.filename.mismatch"
      value="only filenames in camelcase is allowed"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.regexp.regexponfilename;
/*
// xdoc section -- start
.../README.md
.../TestExample.java
.../TestExample.md
.../TestExample.xml
.../Test Example.xml // violation, 'contains whitespace'
.../checkstyle.xml // violation, 'not camelcase'
// xdoc section -- end
*/
class Example5 {}
