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
  int a;
  int b;

  boolean check1() {
    if (cond) { // violation, 'Conditional logic can be removed
      return true;
    }
    else {
      return false;
    }
  }

  // Ok, simplified version of check1()
  boolean check2() {
    return cond;
  }

  boolean check3() {
    if (cond == true) { // violation, 'Conditional logic can be removed'
      return false;
    }
    else {
      return true;
    }
  }

  // Ok, can be simplified but doesn't return a Boolean
  int choose1() {
    if (cond) {
      return a;
    }
    else {
      return b;
    }
  }

  // Ok, simplified version of choose1()
  int choose2() {
    return cond ? a: b;
  }
}
// xdoc section -- end
