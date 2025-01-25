/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Regexp">
      <property name="format" value="(?i)debug"/>
      <property name="illegalPattern" value="true"/>
      <property name="errorLimit" value="1000"/>
    </module>
  </module>
</module>
*/

// violation 8 lines above 'Line matches the illegal pattern.'

package com.puppycrawl.tools.checkstyle.checks.regexp.regexp;

// xdoc section -- start
public class Example10 {
  private void foo() {
    // Debug the code
    // violation above, 'Line matches the illegal pattern'
  }
  private void foo1() {
    // Debug the code
    // violation above, 'Line matches the illegal pattern'
  }
}
// xdoc section -- end
