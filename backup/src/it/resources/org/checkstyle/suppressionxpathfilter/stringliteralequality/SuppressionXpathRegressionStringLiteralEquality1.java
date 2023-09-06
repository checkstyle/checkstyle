package org.checkstyle.suppressionxpathfilter.stringliteralequality;

public class SuppressionXpathRegressionStringLiteralEquality1 {
    public void myFunction(){
        String foo = "pending";
        while (foo != "done") {} // warn
    }
}
