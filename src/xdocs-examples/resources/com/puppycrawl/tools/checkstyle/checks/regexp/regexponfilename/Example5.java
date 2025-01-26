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
.../checkstyle.xml    // violation, 'only filenames in camelcase is allowed'
.../Test Example1.xml // violation, 'only filenames in camelcase is allowed'
.../TestExample2.xml
.../TestExample3.md
.../TestExample4.xml
// xdoc section -- end
*/
class Example5 {}
