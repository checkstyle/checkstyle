/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ParameterAssignment"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.parameterassignment;


import java.util.function.IntBinaryOperator;
import java.util.function.IntPredicate;

// xdoc section -- start
class Example1 {
  int methodOne(int parameter) {
    if (parameter <= 0 ) {
      throw new IllegalArgumentException("A positive value is expected");
    }
    parameter -= 2;  // violation
    return parameter;
  }

  int methodTwo(int parameter) {
    if (parameter <= 0 ) {
      throw new IllegalArgumentException("A positive value is expected");
    }
    int local = parameter;
    local -= 2;  // OK
    return local;
  }

  IntPredicate obj = a -> ++a == 12; // violation
  IntBinaryOperator obj2 = (int a, int b) -> {
    a++;     // violation
    b += 12; // violation
    return a + b;
  };
  IntPredicate obj3 = a -> {
    int b = a; // ok
    return ++b == 12;
  };
}
// xdoc section -- end
