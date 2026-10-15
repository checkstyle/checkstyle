/*
SimplifyBooleanExpression


*/

package org.checkstyle.suppressionxpathfilter.coding.simplifybooleanexpression;

public class InputXpathSimplifyBooleanExpressionAnonymous {

    class Inner{
        boolean a,b,c,d;
        void test(){
            if (a == true) {}; // violation 'Expression can be simplified.'
            boolean e = e = (a && b) ? c : d;
             if (a == b) {};
        }
    }
}
