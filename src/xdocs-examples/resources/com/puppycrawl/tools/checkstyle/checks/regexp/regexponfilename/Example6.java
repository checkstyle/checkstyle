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
/*
// xdoc section -- start
.../TestExample2.xml
.../Example1.java // violation 'File match folder pattern'
// xdoc section -- end
*/
class Example6 {}
