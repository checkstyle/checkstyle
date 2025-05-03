/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SimplifyBooleanReturn"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.simplifybooleanreturn;

// xdoc section -- start
class Example1 {

  boolean cond;
  int a,b;

  boolean check1() {
    if (cond) { // violation, 'Conditional logic can be removed
      return true;
    } else {
      return false;
    }
  }

  boolean check1Simplified() {
    return cond;
  }

  boolean check2() {
    if (cond == true) { // violation, 'Conditional logic can be removed'
      return false;
    } else {
      return true;
    }
  }

  // ok, can be simplified but doesn't return a Boolean
  int choose1() {
    if (cond) {
      return a;
    } else {
      return b;
    }
  }

  int choose1Simplified() {
    return cond ? a: b;
  }
}
// xdoc section -- end
