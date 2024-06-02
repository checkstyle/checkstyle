/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ReturnCount">
      <property name="max" value="3"/>
      <property name="format" value="^$"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.returncount;

// xdoc section -- start
import java.util.function.Predicate;

public class Example4 {
    public Example4() {
    }

    public Example4(int i) {
        return;
    }

    String stringA = "String A";
    String stringB = "String B";

    public int signA() {
        if (stringA.equals(stringB)) {
            return -1;
        }
        return 0;
    } // OK

    public int signB(int x) {
        if (stringA.equals(stringB)) {
            return -1;
        }
        if (x == 0) {
            return 0;
        }
        return 1;
    } // violation, more than three format-accepted return statements

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
    } // violation, more than three format-accepted return statements

    final Predicate<Integer> lambdaA = i -> {
        if (i > 5) {
            return true;
        }
        return false;
    }; // OK

    final Predicate<Integer> lambdaB = i -> {
        return i > 5;
    }; // OK

    public void methodA(int x) {
    }

    public void methodB(int x) {
        return;
    }
}
// xdoc section -- end