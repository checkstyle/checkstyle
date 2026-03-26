/*
<!DOCTYPE module PUBLIC
"-//Checkstyle//DTD Checkstyle Configuration 1.3//EN"
"https://checkstyle.org/dtds/configuration_1_3.dtd">

<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.design.OneTopLevelClassCheck">
      <property name="max" value="1" default="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.treewalker;

/*comment*/
public class InputTreeWalker {
}
// violation below,'Top-level class InputTreeWalkerInner has to reside in its own source file'
class InputTreeWalkerInner {
}
