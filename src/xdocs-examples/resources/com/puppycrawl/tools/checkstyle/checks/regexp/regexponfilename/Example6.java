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
  // violation 'does not match the pattern'
  // ok, Test Example1.xml starts with Test
  // ok, TestExample2.xml starts with Test
  // violation 'does not match the pattern'
  // ok, TestExample4.xml starts with Test
  // violation 'does not match the pattern'
}
// xdoc section -- end
