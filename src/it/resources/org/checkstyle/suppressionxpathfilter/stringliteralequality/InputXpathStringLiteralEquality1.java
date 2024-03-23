package org.checkstyle.suppressionxpathfilter.stringliteralequality;

public class InputXpathStringLiteralEquality1 {
    public void myFunction(){
        String foo = "pending";
        while (foo != "done") {} // warn
    }
}
