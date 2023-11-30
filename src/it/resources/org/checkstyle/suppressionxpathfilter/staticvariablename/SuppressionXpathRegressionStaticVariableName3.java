package org.checkstyle.suppressionxpathfilter.staticvariablename;

public class SuppressionXpathRegressionStaticVariableName3 {

    static class Check{}; //OK
    static SuppressionXpathRegressionStaticVariableName3 VariableName; //warn
}
