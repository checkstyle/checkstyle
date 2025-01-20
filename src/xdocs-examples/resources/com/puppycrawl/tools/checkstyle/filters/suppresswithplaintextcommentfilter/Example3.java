/*xml
<module name="Checker">
  <property name="fileExtensions" value="properties"/>

  <module name="OrderedProperties"/>
  <module name="UniqueProperties"/>

  <module name="SuppressWithPlainTextCommentFilter">
    <property name="offCommentFormat" value="STOP UNIQUE CHECK"/>
    <property name="onCommentFormat" value="RESUME UNIQUE CHECK"/>
    <property name="checkFormat" value="UniquePropertiesCheck"/>
  </module>

</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppresswithplaintextcommentfilter;

public class Example3 { }

// xdoc section -- start
// xdoc section -- end
