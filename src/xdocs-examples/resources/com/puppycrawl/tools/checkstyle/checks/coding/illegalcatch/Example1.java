/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalCatch"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.illegalcatch;

// xdoc section -- start
class Example1 {
  void exampleMethod1() {
    try {
      // some code here
    } catch (Exception e) {
      // violation above, 'Catching 'Exception' is not allowed'
    }
  }

  void exampleMethod2() {
    try {
      // some code here
    } catch (ArithmeticException e) {

    } catch (Exception e) {
      // violation above, 'Catching 'Exception' is not allowed'
    }
  }

  void exampleMethod3() {
    try {
      // some code here
    } catch (NullPointerException e) {
    } catch (OutOfMemoryError e) {

    }
  }

  void exampleMethod4() {
    try {
      // some code here
    } catch (ArithmeticException | NullPointerException e) {

    }
  }

  void exampleMethod5() {
    try {
      // some code here
    } catch (OutOfMemoryError e) {

    }
  }
}
// xdoc section -- end
