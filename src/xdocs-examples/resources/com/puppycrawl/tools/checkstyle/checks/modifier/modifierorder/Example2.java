/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ModifierOrder">
      <property name="modifiersOrder" value="static, final, public, private"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.modifier.modifierorder;

// xdoc section - start
public class Example2 {
  public static final int MAX_VALUE = 100;
  // violation above 'static' modifier out of order with the defined modifier order.

  final private String exampleOne = "ExampleOne";

  static private int exampleTwo;
  // violation below 'static' modifier out of order with the defined modifier order.
  private static void method() {}

  // violation below 'annotation modifier does not precede non-annotation modifiers'
  public @Deprecated class Example {}
}
// xdoc section - end
