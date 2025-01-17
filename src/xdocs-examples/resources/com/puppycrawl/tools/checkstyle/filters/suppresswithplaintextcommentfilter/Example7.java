/*xml
<module name="Checker">
  <property name="fileExtensions" value="sql"/>

  <module name="SuppressWithPlainTextCommentFilter">
    <property name="offCommentFormat" value="CSOFF (\w+) \(\w+\)"/>
    <property name="onCommentFormat" value="CSON (\w+)"/>
    <property name="idFormat" value="$1"/>
  </module>

  <module name="TreeWalker">
    <module name="RegexpSinglelineJava">
      <property name="id" value="count"/>
      <property name="format" value="^.*COUNT(*).*$"/>
      <property name="message"
        value="Don't use COUNT(*), use COUNT(1) instead."/>
    </module>

    <module name="RegexpSinglelineJava">
      <property name="id" value="join"/>
      <property name="format" value="^.*JOIN\s.+\s(ON|USING)$"/>
      <property name="message"
        value="Don't use JOIN, use sub-select instead."/>
    </module>
  </module>

</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppresswithplaintextcommentfilter;

public class Example7 { }

// xdoc section -- start
// xdoc section -- end
