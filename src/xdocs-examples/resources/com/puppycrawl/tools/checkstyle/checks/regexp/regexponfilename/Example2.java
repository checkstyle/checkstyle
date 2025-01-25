/*xml
<module name="Checker">
  <module name="RegexpOnFilename">
    <property name="fileNamePattern" value="^TestExample\d+\.xml$"/>
    <message key="regexp.filename.match"
      value="xml files should not match ''{1}''"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.regexp.regexponfilename;
/*
// xdoc section -- start
.../checkstyle.xml
.../Test Example1.xml
.../TestExample2.xml // violation, 'xml files should not match '^TestExample\d+\.xml$''
.../TestExample3.md
.../TestExample4.xml // violation, 'xml files should not match '^TestExample\d+\.xml$''
// xdoc section -- end
*/
class Example2 {}
