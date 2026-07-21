/*xml
<module name="Checker">
  <module name="RegexpOnFilename">
    <property name="fileNamePattern" value="README"/>
    <property name="fileExtensions" value="md"/>
    <property name="match" value="false"/>
    <message key="regexp.filename.mismatch"
      value="No *.md files other than README.md"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.regexp.regexponfilename;
// xdoc section -- start
class Example3 {
  // ok, checkstyle.xml is not processed
  // ok, Test Example1.xml is not processed
  // ok, TestExample2.xml is not processed
  // violation 'No *.md files other than README.md'
  // ok, TestExample4.xml is not processed
  // ok, Example3.java is not processed
}
// xdoc section -- end
