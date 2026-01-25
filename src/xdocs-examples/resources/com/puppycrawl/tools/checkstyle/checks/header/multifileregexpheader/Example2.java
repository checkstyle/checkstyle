/*xml
<module name="Checker">
  <module name="MultiFileRegexpHeader">
    <property name="fileExtensions" value="java"/>
    <property name="headerFiles"
              value="${config.folder}/java.header, ${config.folder}/apache.header"/>
  </module>
</module>
*/
// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.header.multifileregexpheader;
public class Example2 { }
// xdoc section -- end
// violation 13 lines above 'Header mismatch, expected line content was'
