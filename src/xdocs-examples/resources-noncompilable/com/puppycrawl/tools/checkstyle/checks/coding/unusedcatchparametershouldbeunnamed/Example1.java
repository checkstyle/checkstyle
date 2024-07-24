/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UnusedCatchParameterShouldBeUnnamed"/>
  </module>
</module>
*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.unusedcatchparametershouldbeunnamed;

// xdoc section -- start
class Example1 {

  void test() {

    try {
      int x = 1 / 0;
      // violation below, 'Unused catch parameter 'exception' should be unnamed'
    } catch (Exception exception) {
      System.out.println("infinity");
    }

    try {
      int x = 1 / 0;
      // ok below, 'declared as unnamed parameter'
    } catch (Exception _) {
      System.out.println("infinity");
    }

    try {
      int x = 1 / 0;
      // ok below, ''exception' is used'
    } catch (Exception exception) {
      System.out.println("Got Exception - " +  exception.getMessage());
    }

  }
}
// xdoc section -- end
