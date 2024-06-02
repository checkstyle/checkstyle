/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ReturnCount">
      <property name="maxForVoid" value="0"/>
      <property name="tokens" value="CTOR_DEF"/>
    </module>
    <module name="ReturnCount">
      <property name="max" value="1"/>
      <property name="tokens" value="LAMBDA"/>
    </module>
    <module name="ReturnCount">
      <property name="max" value="2"/>
      <property name="tokens" value="METHOD_DEF"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.returncount;

// xdoc section -- start
import java.util.function.Predicate;

public class Example5 {
    public Example5() {}

    public Example5(int i) { return; } // violation, 'max allowed for void methods/constructors/lambdas is 0'

    public int signA(int x) {
        if (x < -2) { return -1; }
        return 0;
    }

    public int signB(int x) { // violation, 'max allowed for non-void methods/lambdas is 2'
        if (x < -2) { return -1; }
        if (x == 0) { return 0; }
        if (x > 2) { return 2; }
        return 1;
    }

    final Predicate<Integer> lambdaA = i -> { // violation, 'max allowed for non-void methods/lambdas is 1'
        if (i > 5) { return true; }
        return false;
    };

    final Predicate<Integer> lambdaB = i -> { return i > 5; };

    public void methodA(int x) {}

    public void methodB(int x) { return; }
}
// xdoc section -- end
