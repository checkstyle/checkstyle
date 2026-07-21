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
class Example6 {
  // violation, checkstyle.xml does not start with Test
  // ok, Test Example1.xml starts with Test
  // ok, TestExample2.xml starts with Test
  // violation, TestExample3.md does not match the XML pattern
  // ok, TestExample4.xml starts with Test
  // violation, Example6.java does not start with Test
}
// xdoc section -- end
