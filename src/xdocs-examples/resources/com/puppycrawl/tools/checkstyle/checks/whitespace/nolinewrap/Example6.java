/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NoLineWrap">
      <property name="skipAnnotations" value="false"/>
      <property name="tokens" value="CLASS_DEF"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nolinewrap;

// xdoc section -- start
@Deprecated // violation 'should not be line-wrapped'
public class Example6 {
}
// xdoc section -- end
