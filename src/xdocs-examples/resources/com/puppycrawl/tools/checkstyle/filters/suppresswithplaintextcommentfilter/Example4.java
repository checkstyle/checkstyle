/*xml
<module name="Checker">
  <property name="fileExtensions" value="xml"/>

  <module name="SuppressWithPlainTextCommentFilter">
    <property name="offCommentFormat" value="CSOFF\: ([\w\|]+)"/>
    <property name="onCommentFormat" value="CSON\: ([\w\|]+)"/>
    <property name="checkFormat" value="$1"/>
  </module>

</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppresswithplaintextcommentfilter;

public class Example4 { }

// xdoc section -- start
// xdoc section -- end
