/*xml
<module name="Checker">
  <module name="Header">
    <property name="headerFile" value="config/java.header"/>
    <property name="ignoreLines" value="2, 3, 4"/>
    <property name="fileExtensions" value="java"/>
  </module>
</module>
*/
// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.header.header;
/* violation on first line 'Missing a header - not enough lines in file' */
// due to values at 'ignoreLines'
public class Example2 { }
// xdoc section -- end
// violation 15 lines above 'Missing a header - not enough lines in file'
