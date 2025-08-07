package org.checkstyle.suppressionxpathfilter.coding.stringliteralequality;

public class InputXpathStringLiteralEqualityFalse {
    public void myFunction(){
        String foo = "pending";
        while (foo != "done") {} // warn
    }
}
