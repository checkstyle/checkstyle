package org.checkstyle.suppressionxpathfilter.stringliteralequality;

public class InputXpathStringLiteralEqualityExp {
    public void myFunction(){
        String foo = "pending";
        boolean flag = (foo == "done"); // warn
    }
}
