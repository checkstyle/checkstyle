package org.checkstyle.checks.suppressionxpathfilter.stringliteralequality;

public class InputXpathStringLiteralEqualityFalse {
    public void myFunction(){
        String foo = "pending";
        while (foo != "done") {} // warn
    }
}
