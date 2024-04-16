package org.checkstyle.suppressionxpathfilter.stringliteralequality;

public class InputXpathStringLiteralEqualityTrue {
    public void myFunction(){
        String foo = "pending";
        if (foo == "done") {} // warn
    }
}
