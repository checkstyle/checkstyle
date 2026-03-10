/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalToken"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.illegaltoken;

// xdoc section -- start
class Example1 {
<<<<<<< HEAD

  void InvalidExample() {
    // violation, 'Using 'outer:' is not allowed'
    outer:
=======
  native void InvalidExample();

  void anotherMethod() {
    outer: // violation, 'Using 'outer:' is not allowed'
>>>>>>> upstream/master
    for (int i = 0; i < 5; i++) {
      if (i == 1) {
        break outer;
      }
    }
  }
}
// xdoc section -- end