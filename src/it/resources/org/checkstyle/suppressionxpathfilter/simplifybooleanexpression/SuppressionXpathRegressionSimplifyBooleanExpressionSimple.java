package org.checkstyle.suppressionxpathfilter.simplifybooleanexpression;

public class SuppressionXpathRegressionSimplifyBooleanExpressionSimple {
    private Object c,d,e;
    boolean a,b;
    public void test(){
        boolean f = c == null ? false : c.equals(d); // ok
        if (!false) {} // warn
        e = (a && b) ? c : d; // ok
    }
}
