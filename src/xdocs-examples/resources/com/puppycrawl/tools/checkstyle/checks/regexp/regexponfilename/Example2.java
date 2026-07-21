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
// xdoc section -- start
class Example2 {
  // ok, checkstyle.xml
  // ok, Test Example1.xml
  // violation, TestExample2.xml matches the forbidden pattern
  // ok, TestExample3.md
  // violation, TestExample4.xml matches the forbidden pattern
  // ok, Example2.java
}
// xdoc section -- end
