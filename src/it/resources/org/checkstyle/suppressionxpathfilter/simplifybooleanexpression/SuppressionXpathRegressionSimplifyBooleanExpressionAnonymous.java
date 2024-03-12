package org.checkstyle.suppressionxpathfilter.simplifybooleanexpression;

public class SuppressionXpathRegressionSimplifyBooleanExpressionAnonymous {

    class Inner{
        boolean a,b,c,d;
        void test(){
            if (a == true) {}; // warn
            boolean e = e = (a && b) ? c : d; // ok
             if (a == b) {}; // ok
        }
    }
}
