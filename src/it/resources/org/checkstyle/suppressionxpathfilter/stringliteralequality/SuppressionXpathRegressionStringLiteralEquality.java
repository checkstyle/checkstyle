package org.checkstyle.suppressionxpathfilter.stringliteralequality;

public class SuppressionXpathRegressionStringLiteralEquality {
    public void myFunction(){
        String foo = "pending";
        if (foo == "done") {} // warn
    }
}
