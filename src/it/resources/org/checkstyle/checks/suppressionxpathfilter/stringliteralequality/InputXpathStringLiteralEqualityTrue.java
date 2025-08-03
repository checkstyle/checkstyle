package org.checkstyle.checks.suppressionxpathfilter.stringliteralequality;

public class InputXpathStringLiteralEqualityTrue {
    public void myFunction(){
        String foo = "pending";
        if (foo == "done") {} // warn
    }
}
