package org.checkstyle.suppressionxpathfilter.parameteralignment;

public class InputXpathParameterAlignmentCtor {
    int field;
    InputXpathParameterAlignmentCtor(int field) {
        int i = field;
        this.field = field;
        i++;
        field += 1;
        this.field++;
    }
    // without parameters
    InputXpathParameterAlignmentCtor() {
        field = 0;
    }
    InputXpathParameterAlignmentCtor(char a,
            int b) { // warn

    }

}
