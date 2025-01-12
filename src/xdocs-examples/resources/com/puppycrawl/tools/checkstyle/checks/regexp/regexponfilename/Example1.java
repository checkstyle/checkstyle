/*xml
<module name="Checker">
  <module name="RegexpOnFilename"/>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.regexp.regexponfilename;

// xdoc section -- start
/*checks/regexp/regexponfilename/Example1.java  //OK, contains no whitespace
/*checks/regexp/regexponfilename/Example 1.java //Not OK, contains whitespace */
public class Example1 {}
// xdoc section -- end
