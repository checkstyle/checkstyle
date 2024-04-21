package org.checkstyle.suppressionxpathfilter.stringliteralequality;

public class InputXpathStringLiteralEqualityFalse {
    public void myFunction(){
        String foo = "pending";
        while (foo != "done") {} // warn
    }
}
