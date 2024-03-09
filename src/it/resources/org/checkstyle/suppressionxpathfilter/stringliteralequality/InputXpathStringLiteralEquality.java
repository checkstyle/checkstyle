package org.checkstyle.suppressionxpathfilter.stringliteralequality;

public class InputXpathStringLiteralEquality {
    public void myFunction(){
        String foo = "pending";
        if (foo == "done") {} // warn
    }
}
