package org.checkstyle.suppressionxpathfilter.parameterassignment;

public class InputXpathParameterAssignmentCtor {
    int field;
    InputXpathParameterAssignmentCtor(int field) {
        int i = field;
        this.field = field;
        i++;
        field += 1; // warn
        this.field++;
    }
    // without parameters
    InputXpathParameterAssignmentCtor() {
        field = 0;
    }
}
