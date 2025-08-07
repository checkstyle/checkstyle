package org.checkstyle.suppressionxpathfilter.coding.stringliteralequality;

public class InputXpathStringLiteralEqualityTrue {
    public void myFunction(){
        String foo = "pending";
        if (foo == "done") {} // warn
    }
}
