/*xml
<module name="Checker">
  <property name="fileExtensions" value="sql"/>

  <module name="FileTabCharacter">
    <property name="eachLine" value="true"/>
  </module>

  <module name="SuppressWithPlainTextCommentFilter">
    <property name="offCommentFormat" value="stop tab check"/>
    <property name="onCommentFormat" value="resume tab check"/>
    <property name="checkFormat" value="FileTabCharacterCheck"/>
  </module>

</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppresswithplaintextcommentfilter;

public class Example3 { }

// xdoc section -- start
// xdoc section -- end
