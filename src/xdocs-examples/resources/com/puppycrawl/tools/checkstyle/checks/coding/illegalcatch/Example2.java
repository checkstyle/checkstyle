/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalCatch">
      <property name="illegalClassNames" value="ArithmeticException,
                  OutOfMemoryError"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.illegalcatch;

// xdoc section -- start
class Example2 {
  void exampleMethod1() {
    try {
      // some code here
    } catch (Exception e) {
    }
  }

  void exampleMethod2() {
    try {
      // some code here
    } catch (ArithmeticException e) {
      // violation above, 'Catching 'ArithmeticException' is not allowed'
      //order of catch blocks doesn't matter
    }
    catch(Exception e){
    }
  }

  void exampleMethod3() {
    try {
      // some code here
    } catch (NullPointerException e) {
    } catch (OutOfMemoryError e) {
      // violation above, 'Catching 'OutOfMemoryError' is not allowed'
    }
  }

  void exampleMethod4() {
    try {
      // some code here
    } catch (ArithmeticException | NullPointerException e) {
      // violation above, 'Catching 'ArithmeticException' is not allowed'
    }
  }

  void exampleMethod5(){
    try {
      // some code here
    } catch (OutOfMemoryError e) {
      // violation above, 'Catching 'OutOfMemoryError' is not allowed'
    }
  }
}
// xdoc section -- end
