/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SimplifyBooleanExpression"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.simplifybooleanexpression;

// xdoc section - start
class Example1 {
  void InvalidExample() {
    boolean a=true;
    boolean b=true;
    Object c=null;
    Object d=null;
    Object e=null;
    if (!false) {}; // violation 'Expression can be simplified'
    if (a == true) {}; // violation 'Expression can be simplified'
    if (a == b) {};
    if (a == false) {}; // violation 'Expression can be simplified'
    if (!(a != true)) {}; // violation 'Expression can be simplified'
    e = (a || b) ? c : d;
    e = (a || false) ? c : d; // violation 'Expression can be simplified'
    e = (a && b) ? c : d;
    int s = 12;
    boolean m = s > 1 ? true : false; // violation 'Expression can be simplified'
    boolean f = c == null ? false : c.equals(d);
  }
}
// xdoc section - end
