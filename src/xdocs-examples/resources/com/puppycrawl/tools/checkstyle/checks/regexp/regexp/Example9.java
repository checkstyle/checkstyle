/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Regexp">
      <property name="format" value="(?i)debug"/>
      <property name="illegalPattern" value="true"/>
    </module>
  </module>
</module>
*/

// violation 7 lines above 'Line matches the illegal pattern.'

package com.puppycrawl.tools.checkstyle.checks.regexp.regexp;

// xdoc section -- start
public class Example9 {
  private void foo() {
    // Debug the code
    // violation above, 'Line matches the illegal pattern.'
  }
}
// xdoc section -- end
