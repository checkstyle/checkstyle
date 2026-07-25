/*xml
<module name="Checker">
  <module name="RegexpOnFilename"/>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.regexp.regexponfilename;
// xdoc section -- start
/*
.../checkstyle.xml
.../Test Example1.xml // violation 'File match folder pattern '' and file pattern '\s'.'
.../TestExample2.xml
.../TestExample3.md
.../TestExample4.xml
.../Example1.java
*/
class Example1{}
// xdoc section -- end
