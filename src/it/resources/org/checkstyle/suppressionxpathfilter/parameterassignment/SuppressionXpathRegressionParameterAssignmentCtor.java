package org.checkstyle.suppressionxpathfilter.parameterassignment;

public class SuppressionXpathRegressionParameterAssignmentCtor {
    int field;
    SuppressionXpathRegressionParameterAssignmentCtor(int field) {
        int i = field;
        this.field = field;
        i++;
        field += 1; // warn
        this.field++;
    }
    // without parameters
    SuppressionXpathRegressionParameterAssignmentCtor() {
        field = 0;
    }
}
