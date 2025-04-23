/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SimplifyBooleanExpression"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.simplifybooleanexpression;

// xdoc section -- start
class Example1 {
  void InvalidExample() {
    boolean a = true;
    boolean b = true;
    Object c = null;
    Object d = null;
    Object e = null;
    if (!false) {}; // violation, can be simplified to true
    if (a == true) {}; // violation, can be simplified to a
    if (a == b) {};
    if (a == false) {}; // violation, can be simplified to !a
    if (!(a != true)) {}; // violation, can be simplified to a
    e = (a || b) ? c : d;
    e = (a || false) ? c : d; // violation, can be simplified to a
    e = (a && b) ? c : d;
    int s = 12;
    boolean m = s > 1 ? true : false; // violation, can be simplified to s > 1
    boolean f = c == null ? false : c.equals(d);
  }
}
// xdoc section -- end
