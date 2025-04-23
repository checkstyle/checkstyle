/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AvoidInlineConditionals"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.avoidinlineconditionals;

// xdoc section -- start
public class Example1 {
  public void InvalidExample( String str) {
    int x = 5;
    boolean foobar = (x == 5);
    String text = null;
    text = (text == null) ? "" : text; // violation, 'Avoid inline conditionals'
    String b;
    if (str != null && str.length() >= 1) {
      b = str.substring(1);
    }
    else {
      b = null;
    }
    // violation below, 'Avoid inline conditionals'
    b = (str != null && str.length() >= 1) ? str.substring(1) : null;
  }
}
// xdoc section -- end
