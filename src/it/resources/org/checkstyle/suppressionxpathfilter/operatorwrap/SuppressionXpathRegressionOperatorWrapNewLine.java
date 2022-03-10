package org.checkstyle.suppressionxpathfilter.operatorwrap;

public class SuppressionXpathRegressionOperatorWrapNewLine {

    void test() {
        int x = 1 + // warn
             2
             -
             3
             -
             4;
     x = x + 2;

   }

   void test2() {
        int x = 1
             +
             2
             -
             3
             -
             4;
     x = x + 2;

   }
}
