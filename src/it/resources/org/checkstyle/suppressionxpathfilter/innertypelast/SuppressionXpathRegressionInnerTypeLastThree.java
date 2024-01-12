package org.checkstyle.suppressionxpathfilter.innertypelast;

public class SuppressionXpathRegressionInnerTypeLastThree {

    static {} // OK

    interface Inner {
    }

    public SuppressionXpathRegressionInnerTypeLastThree() { // warn
    }

}
