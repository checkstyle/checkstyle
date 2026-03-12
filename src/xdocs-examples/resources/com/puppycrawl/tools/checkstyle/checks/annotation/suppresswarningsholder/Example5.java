/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ConstantName"/>
    <module name="SuppressWarningsHolder">
      <property name="aliasList" value="ConstantName=mycustom"/>
    </module>
  </module>
  <module name="SuppressWarningsFilter"/>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarningsholder;

// xdoc section -- start
public class Example5 {
  private static final int i = 0; // violation, 'Name 'i' must match pattern'
  @SuppressWarnings("mycustom")
  private static final int m = 0; // violation suppressed
}
// xdoc section -- end
