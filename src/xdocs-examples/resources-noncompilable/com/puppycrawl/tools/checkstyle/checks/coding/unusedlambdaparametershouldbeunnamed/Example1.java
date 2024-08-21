/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UnusedLambdaParameterShouldBeUnnamed"/>
  </module>
</module>
*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.unusedlambdaparametershouldbeunnamed;

import java.util.function.BiFunction;
import java.util.function.Function;

// xdoc section -- start
class Example1 {

  int x;

  void test() {
    // violation below, 'Unused lambda parameter 'x' should be unnamed'
    Function<Integer, Integer> f1 = (x) -> this.x + 1;
    // ok below, parameter is unnamed
    Function<Integer, Integer> f2 = (_) -> this.x + 1;
    // ok below, parameter is used
    Function<Integer, Integer> f3 = (x) -> x + 1;
    // violation below, 'Unused lambda parameter 'y' should be unnamed'
    BiFunction<Integer, Integer, Integer> bf1 = (x, y) -> x + this.x;
  }
}
// xdoc section -- end
