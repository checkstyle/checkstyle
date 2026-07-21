/*xml
<module name="Checker">
  <module name="RegexpOnFilename"/>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.regexp.regexponfilename;
// xdoc section -- start
class Example1 {
  // ok, checkstyle.xml
  // violation 'File name 'Test Example1.xml' matches the pattern '\s'.'
  // ok, TestExample2.xml
  // ok, TestExample3.md
  // ok, TestExample4.xml
  // ok, Example1.java
}
// xdoc section -- end
