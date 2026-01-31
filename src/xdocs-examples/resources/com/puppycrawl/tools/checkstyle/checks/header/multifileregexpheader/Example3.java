/*xml
<module name="Checker">
    <module name="MultiFileRegexpHeader">
        <property name="headerFiles"
                  value="${config.folder}/java.header,
                  ${config.folder}/apache.header"/>
    </module>
</module>
*/
// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.header.multifileregexpheader;
/* violation on first line 'Header mismatch, expected line content was' */
public class Example3 { }
// xdoc section -- end
