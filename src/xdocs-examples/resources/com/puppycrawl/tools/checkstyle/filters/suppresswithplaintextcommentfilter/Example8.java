/*xml
<module name="Checker">
  <property name="fileExtensions" value="sql"/>

  <module name="RegexpSingleline">
    <property name="format" value="^.*JOIN\s.+\s(ON|USING)$"/>
    <property name="message" value="Don't use JOIN, use sub-select instead."/>
  </module>

  <module name="LineLength">
    <property name="max" value="60"/>
  </module>

  <module name="SuppressWithPlainTextCommentFilter">
    <property name="offCommentFormat" value="CSOFF\: ([\w\|]+)"/>
    <property name="onCommentFormat" value="CSON\: ([\w\|]+)"/>
    <property name="checkFormat" value="$1"/>
  </module>

</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppresswithplaintextcommentfilter;

public class Example8 { }

// xdoc section -- start
// xdoc section -- end
