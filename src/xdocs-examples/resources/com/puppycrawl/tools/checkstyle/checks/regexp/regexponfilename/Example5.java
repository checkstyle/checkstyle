/*xml
<module name="Checker">
  <module name="RegexpOnFilename">
    <property name="fileNamePattern" value="^([A-Z][a-z0-9]+\.?)+$"/>
    <property name="match" value="false"/>
    <property name="ignoreFileNameExtensions" value="true"/>
    <message key="regexp.filename.mismatch"
      value="only filenames in camelcase is allowed"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.regexp.regexponfilename;
// xdoc section -- start
class Example5 {
  // violation, checkstyle.xml is not camel case
  // violation, Test Example1.xml is not camel case
  // ok, TestExample2.xml
  // ok, TestExample3.md
  // ok, TestExample4.xml
  // ok, Example5.java
}
// xdoc section -- end
