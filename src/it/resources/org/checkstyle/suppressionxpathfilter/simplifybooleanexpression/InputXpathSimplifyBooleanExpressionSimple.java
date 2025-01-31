package org.checkstyle.suppressionxpathfilter.simplifybooleanexpression;

public class InputXpathSimplifyBooleanExpressionSimple {
    private Object c,d,e;
    boolean a,b;
    public void test(){
        boolean f = c == null ? false : c.equals(d);  
        if (!false) {} // warn
        e = (a && b) ? c : d;  
    }
}
