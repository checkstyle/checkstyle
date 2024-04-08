package org.checkstyle.suppressionxpathfilter.constructorsdeclarationgrouping;

public enum SuppressionXpathRegressionConstructorsDeclarationGrouping2 {
    ONE;

    SuppressionXpathRegressionConstructorsDeclarationGrouping2() {}

    SuppressionXpathRegressionConstructorsDeclarationGrouping2(String str) {}

    int f;

    SuppressionXpathRegressionConstructorsDeclarationGrouping2(int x) {} // warn
}
