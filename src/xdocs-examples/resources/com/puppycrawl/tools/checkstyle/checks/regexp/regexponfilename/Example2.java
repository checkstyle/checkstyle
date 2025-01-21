/*xml
<module name="Checker">
  <module name="RegexpOnFilename">
    <property name="fileNamePattern" value="^TestExample\d+\.xml$"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.regexp.regexponfilename;
/*
// xdoc section -- start
.../README.md
.../TestExample.java
.../TestExample.md
.../TestExample.xml // violation, '.xml file not allowed'
.../Test Example.xml // violation, 'contains whitespace'
.../checkstyle.xml
// xdoc section -- end
*/
class Example2 {}
