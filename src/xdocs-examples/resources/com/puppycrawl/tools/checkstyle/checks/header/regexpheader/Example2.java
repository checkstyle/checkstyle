/*xml
<module name="Checker">
  <module name="RegexpHeader">
    <property name="headerFile" value="${config.folder}/java.header"/>
    <property name="multiLines" value="10, 13"/>
  </module>
</module>
*/
// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.header.regexpheader;
/* violation on first line 'Missing a header - not enough lines in file.' */
public class Example2 { }
// xdoc section -- end
// violation 13 lines above 'Missing a header - not enough lines in file.'
