/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ReturnCount">
      <property name="max" value="3"/>
      <property name="maxForVoid" value="0"/>
      <property name="format" value="^Example4$"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.returncount;

import java.util.function.Predicate;

// xdoc section - start
public class Example4 {
    public Example4() {}
    // ok below, because 'format' excludes constructors named 'Example4'
    public Example4(int i) { return; }

    public int signA(int x) {
        if (x < -2) { return -1; }
        return 0;
    }
    // violation below 'max allowed for non-void methods/lambdas is 3'
    public int signB(int x) {
        if (x < -2) { return -1; }
        if (x == 0) { return 0; }
        if (x > 2) { return 2; }
        return 1;
    }
    // ok below, because non-void restriction is 3
    final Predicate<Integer> lambdaA = i -> {
        if (i > 5) { return true; }
        return false;
    };

    final Predicate<Integer> lambdaB = i -> { return i > 5; };

    public void methodA(int x) {}
    // violation below 'max allowed for void methods/constructors/lambdas is 0'
    public void methodB(int x) { return; }
}
// xdoc section - end
