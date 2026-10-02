/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ModifierOrder"/>
  </module>
</module>
*/




package com.puppycrawl.tools.checkstyle.checks.modifier.modifierorder;

// xdoc section - start
public class Example1 {
  public static final int MAX_VALUE = 100;

  // violation below "'public' modifier out of order with the JLS suggestions"
  final public String exampleOne = "ExampleOne";
  // violation below "'public' modifier out of order with the JLS suggestions"
  static public int exampleTwo;

  private static void method() {}

  // violation below 'annotation modifier does not precede non-annotation modifiers'
  public @Deprecated class Example {}

  sealed strictfp interface Test permits TestClass {}

  final class TestClass implements Test {}

}
// xdoc section - end
