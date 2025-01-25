/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Regexp">
      <property name="format" value="(?i)fix me\."/>
      <property name="illegalPattern" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.regexp.regexp;

// xdoc section -- start
public class Example9 {
  private void foo() {
    // fix me.
    // violation above, 'Line matches the illegal pattern.'
  }
}
// xdoc section -- end
