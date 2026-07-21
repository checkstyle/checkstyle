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
  // violation 'File name 'checkstyle.xml' does not match the pattern '^Test.*\\.xml$'.'
  // ok, Test Example1.xml starts with Test
  // ok, TestExample2.xml starts with Test
  // violation 'File name 'TestExample3.md' does not match the pattern '^Test.*\\.xml$'.'
  // ok, TestExample4.xml starts with Test
  // violation 'File name 'Example6.java' does not match the pattern '^Test.*\\.xml$'.'
}
// xdoc section -- end
