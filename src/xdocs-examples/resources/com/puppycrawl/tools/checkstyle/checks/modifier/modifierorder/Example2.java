/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ModifierOrder">
      <property name="modifierOrder" value="openjdk"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.modifier.modifierorder;

// xdoc section -- start
public class Example2 {
  public static final int MAX_VALUE = 100;

  // ok, OpenJDK style allows static before final
  private static final String example = "Example";

  // violation below 'annotation modifier does not precede non-annotation modifiers'
  public @Deprecated class Example {}
}
// xdoc section -- end
