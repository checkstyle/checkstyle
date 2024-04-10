package org.checkstyle.suppressionxpathfilter.constructorsdeclarationgrouping;

public class SuppressionXpathRegressionConstructorsDeclarationGrouping1 {
    SuppressionXpathRegressionConstructorsDeclarationGrouping1() {}

    SuppressionXpathRegressionConstructorsDeclarationGrouping1(int a) {}

    int x;

    SuppressionXpathRegressionConstructorsDeclarationGrouping1(String str) {} // warn
}
