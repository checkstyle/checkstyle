/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ModifierOrder">
      <property name="order"
          value="public, protected, private, abstract, static, final,
                 transient, volatile, default, synchronized, native, strictfp"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.modifier.modifierorder;

// xdoc section -- start
public class Example2 {
  public static final int MAX_VALUE = 100;

  // violation below "'static' modifier out of order with the JLS suggestions"
  private final static String S = "x";

  // OK
  private static final String OK = "y";
}
// xdoc section -- end
