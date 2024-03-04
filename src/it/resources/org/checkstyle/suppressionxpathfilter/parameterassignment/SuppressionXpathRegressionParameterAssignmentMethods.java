package org.checkstyle.suppressionxpathfilter.parameterassignment;

public class SuppressionXpathRegressionParameterAssignmentMethods {
    int field;
    void Test1(int field) {
        int i = field;
        this.field = field;
        i++;
        field += 1; // warn
        this.field++;
    }
    // without parameters
    void Test2() {
        field = 0;
    }
}
