/*
SimplifyBooleanExpression


*/

package org.checkstyle.suppressionxpathfilter.coding.simplifybooleanexpression;

public class InputXpathSimplifyBooleanExpressionSimple {
    private Object c,d,e;
    boolean a,b;
    public void test(){
        boolean f = c == null ? false : c.equals(d);
        if (!false) {} // violation 'Expression can be simplified.'
        e = (a && b) ? c : d;
    }
}
