/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ExpressionOverBlockLambda"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.expressionoverblocklambda;

import java.util.function.Function;

// xdoc section - start
public class Example1 {
  public void foo() {
    // violation below 'preferred over single-line'
    Runnable a = () -> { System.out.println("hello"); };

    // violation below 'preferred over single-line'
    Function<Integer, Integer> b = x -> { return x + 1; };

    // ok - expression lambda
    Runnable c = () -> System.out.println("hello");

    // ok - multi-line block lambda
    Runnable d = () -> {
      System.out.println("hello");
    };

    // ok - void return
    Runnable e = () -> { return; };

    // ok - multi-statement block
    Runnable f = () -> {
      System.out.println("hello");
      System.out.println("world");
    };
  }
}
// xdoc section - end
