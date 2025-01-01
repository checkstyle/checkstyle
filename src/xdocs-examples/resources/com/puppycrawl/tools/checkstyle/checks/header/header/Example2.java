/*xml
<module name="Checker">
  <module name="Header">
    <property name="headerFile" value="${config.folder}/java.header"/>
    <property name="ignoreLines" value="2, 3, 4"/>
    <property name="fileExtensions" value="java"/>
  </module>
</module>
*/



// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.header.header;
/* violation on first line 'Line does not match expected header line of' */
// because headerFile is bigger then target java file
public class Example2 { }
// xdoc section -- end
// violation 18 lines above 'Line does not match expected header line of'
