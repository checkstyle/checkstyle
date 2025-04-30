/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ModifierOrder"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.modifier.modifierorder;

// xdoc section -- start
public class Example1 {
  public static final int MAX_VALUE = 100;

  // violation below "'private' modifier out of order with the JLS suggestions"
  final private String example = "Example";

  // violation below, 'annotation modifier does not precede non-annotation modifiers'
  public @Deprecated class Example {}
}
// xdoc section -- end
