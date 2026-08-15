/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ModifierOrder">
      <property name="modifiersOrder" value="public, private,
                protected, abstract, static, final, transient, volatile,
                default, synchronized, native, strictfp"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.modifier.modifierorder;

// xdoc section - start
public class Example2 {
  public static final int MAX_VALUE = 100;

  // violation below 'public' modifier out of order with the defined modifier order.
  final public String exampleOne = "ExampleOne";
  // violation below 'public' modifier out of order with the defined modifier order.
  static public int exampleTwo;

  private static void method() {}

  // violation below 'annotation modifier does not precede non-annotation modifiers'
  public @Deprecated class Example {}

  sealed strictfp interface Test permits TestClass {}
  // violation above 'strictfp' modifier out of order.
  final class TestClass implements Test {}

}
// xdoc section - end
