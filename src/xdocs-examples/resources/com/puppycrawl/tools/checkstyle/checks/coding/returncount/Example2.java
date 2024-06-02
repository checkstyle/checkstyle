/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ReturnCount">
      <property name="maxForVoid" value="0"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.returncount;

// xdoc section -- start
import java.util.function.Predicate;

public class Example2 {
    public Example2() {
    }

    public Example2(int i) { // violation, 'max allowed for void methods/constructors/lambdas is 0'
        return;
    }

    String stringA = "String A";
    String stringB = "String B";

    public int signA() {
        if (stringA.equals(stringB)) {
            return -1;
        }
        return 0;
    }

    public int signB(int x) { // violation, 'max allowed for non-void methods/lambdas is 2'
        if (stringA.equals(stringB)) {
            return -1;
        }
        if (x == 0) {
            return 0;
        }
        return 1;
    }

    public int signC(int x) { // violation, 'max allowed for non-void methods/lambdas is 2'
        if (stringA.equals(stringB)) {
            return -1;
        }
        if (x == 0) {
            return 0;
        }
        if (x > 2) {
            return 2;
        }
        return 1;
    }

    final Predicate<Integer> lambdaA = i -> {
        if (i > 5) {
            return true;
        }
        return false;
    };

    final Predicate<Integer> lambdaB = i -> {
        return i > 5;
    };

    public void methodA(int x) {
    }

    public void methodB(int x) { // violation, 'max allowed for void methods/constructors/lambdas is 0'
        return;
    }
}
// xdoc section -- end
