/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Regexp">
      <!-- . matches any character, so we need to escape it and
       use \. to match dots. -->
      <property name="format" value="System\.out\.println"/>
      <property name="illegalPattern" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.regexp.regexp;

public class Example5 {}

// xdoc section -- start
// xdoc section -- end
