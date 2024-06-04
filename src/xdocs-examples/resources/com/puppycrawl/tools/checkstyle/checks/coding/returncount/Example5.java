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
    public Example5() {
    }

    public Example5(int i) {
        return;
    } // violation above, 'Return count is 1 (max allowed for void constructors is 0)'

    String stringA = "String A";
    String stringB = "String B";

    public int signA() {
        if (stringA.equals(stringB)) {
            return -1;
        }
        return 0;
    }

    public int signB(int x) {
        if (stringA.equals(stringB)) {
            return -1;
        }
        if (x == 0) {
            return 0;
        }
        return 1;
    } // violation above, 'Return count is 3 (max allowed for non-void methods is 2)'

    public int signC(int x) {
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
    } // violation above, 'Return count is 4 (max allowed for non-void methods is 2)'

    final Predicate<Integer> lambdaA = i -> {
        if (i > 5) {
            return true;
        }
        return false;
    }; // violation above, 'Return count is 2 (max allowed for non-void lambdas is 1)'

    final Predicate<Integer> lambdaB = i -> {
        return i > 5;
    };

    public void methodA(int x) {
    }

    public void methodB(int x) {
        return;
    }
}
// xdoc section -- end
