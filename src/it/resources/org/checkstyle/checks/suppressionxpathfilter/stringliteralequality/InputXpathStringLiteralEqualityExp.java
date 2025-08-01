package org.checkstyle.checks.suppressionxpathfilter.stringliteralequality;

public class InputXpathStringLiteralEqualityExp {
    public void myFunction(){
        String foo = "pending";
        boolean flag = (foo == "done"); // warn
    }
}
