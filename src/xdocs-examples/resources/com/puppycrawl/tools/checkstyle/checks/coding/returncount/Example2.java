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
    } // OK

    public Example2(int i) {
        return;
    } // violation, more than zero return statements per void method

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
    } // violation, more than default two return statements

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
    } // violation, more than default two return statements

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
    } // OK

    public void methodB(int x) {
        return;
    } // violation, more than zero return statements per void method
}
// xdoc section -- end