package org.checkstyle.suppressionxpathfilter.simplifybooleanexpression;

public class SuppressionXpathRegressionSimplifyBooleanExpressionInterface {
    interface Inner {
        default void test() {
           boolean a = false, b = false, c = false, d = false;
           if (!(b != true)) {}; // warn
           boolean e = e = (a && b) ? c : d; // ok
           if (a == b) {}; // ok

       }
    }
}
