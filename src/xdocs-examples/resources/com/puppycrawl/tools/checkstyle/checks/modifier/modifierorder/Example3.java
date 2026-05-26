/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ModifierOrder">
      <property name="modifierOrder"
               value="public,protected,private,static,final,abstract"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.modifier.modifierorder;

// xdoc section -- start
public class Example3 {
  public static final int MAX_VALUE = 100;

  // ok, custom order allows static before final
  private static final String example = "Example";

  // violation below ''static' modifier out of order with the configured modifier order.'
  private final static String WRONG = "Wrong";
}
// xdoc section -- end
