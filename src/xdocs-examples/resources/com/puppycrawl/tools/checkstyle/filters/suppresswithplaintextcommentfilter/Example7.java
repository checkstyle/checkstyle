/*xml
<module name="Checker">
  <property name="fileExtensions" value="xml"/>

  <module name="RegexpSingleline">
    <property name="id" value="typeCode"/>
    <property name="format"
        value="param\s+name=&quot;type&quot;\s+value=&quot;code&quot;"/>
    <property name="message"
        value="Type code is not allowed. Use type raw instead."/>
  </module>

  <module name="RegexpSingleline">
    <property name="id" value="typeConfig"/>
    <property name="format"
        value="param\s+name=&quot;type&quot;\s+value=&quot;config&quot;"/>
    <property name="message" value="Type config is not allowed in this file."/>
  </module>

  <module name="SuppressWithPlainTextCommentFilter">
    <property name="offCommentFormat" value="CSOFF (\w+) \(([\w\s]+)\)"/>
    <property name="onCommentFormat" value="CSON (\w+)"/>
    <property name="idFormat" value="$1"/>
  </module>

</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppresswithplaintextcommentfilter;

public class Example7 { }

// xdoc section -- start
// xdoc section -- end
