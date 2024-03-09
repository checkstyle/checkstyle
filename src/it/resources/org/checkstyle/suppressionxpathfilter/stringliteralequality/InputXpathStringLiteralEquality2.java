package org.checkstyle.suppressionxpathfilter.stringliteralequality;

public class InputXpathStringLiteralEquality2 {
    public void myFunction(){
        String foo = "pending";
        boolean flag = (foo == "done"); // warn
    }
}
