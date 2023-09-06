package org.checkstyle.suppressionxpathfilter.stringliteralequality;

public class SuppressionXpathRegressionStringLiteralEquality2 {
    public void myFunction(){
        String foo = "pending";
        boolean flag = (foo == "done"); // warn
    }
}
