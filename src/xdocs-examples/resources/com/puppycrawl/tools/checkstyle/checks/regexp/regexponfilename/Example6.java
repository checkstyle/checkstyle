/*xml
<module name="Checker">
  <module name="RegexpOnFilename">
    <property name="folderPattern" value="[\\/]regexponfilename$"/>
    <property name="fileNamePattern" value="^Test.*\.xml$"/>
    <property name="match" value="false"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.regexp.regexponfilename;
// xdoc section -- start
/*
.../checkstyle.xml
.../Test Example1.xml
.../TestExample2.xml
.../TestExample3.md
.../TestExample4.xml
.../Example1.java // violation 'File match folder pattern'
*/
class Example6 {}
// xdoc section -- end
