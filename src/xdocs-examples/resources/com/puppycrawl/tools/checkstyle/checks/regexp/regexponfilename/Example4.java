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
// xdoc section -- start
class Example4 {
  // ok, checkstyle.xml matches an allowed extension
  // ok, Test Example1.xml matches an allowed extension
  // ok, TestExample2.xml matches an allowed extension
  // violation 'Only property and xml files to be located in the resource folder'
  // ok, TestExample4.xml matches an allowed extension
  // violation 'Only property and xml files to be located in the resource folder'
}
// xdoc section -- end
