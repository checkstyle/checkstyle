/*xml
<module name="Checker">
    <module name="MultiFileRegexpHeader">
        <property name="fileExtensions" value="java"/>
        <property name="headerFiles" value="${config.folder}/java.header,
        ${config.folder}/apache.header"/>
    </module>
</module>
*/
// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.header.multifileregexpheader;
/* violation on first line 'Header mismatch, expected line content was' */
// because headerFile is bigger then target java file
public class Example2 { }
// xdoc section -- end
