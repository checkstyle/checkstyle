/*
ParameterAssignment


*/

package org.checkstyle.suppressionxpathfilter.coding.parameterassignment;

public class InputXpathParameterAssignmentCtor {
    int field;
    InputXpathParameterAssignmentCtor(int field) {
        int i = field;
        this.field = field;
        i++;
        field += 1; // violation "Assignment of parameter 'field' is not allowed."
        this.field++;
    }
    // without parameters
    InputXpathParameterAssignmentCtor() {
        field = 0;
    }
}
