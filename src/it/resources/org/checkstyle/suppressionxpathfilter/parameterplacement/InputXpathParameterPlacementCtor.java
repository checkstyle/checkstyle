package org.checkstyle.suppressionxpathfilter.parameterplacement;

public class InputXpathParameterPlacementCtor {
    int field;
    InputXpathParameterPlacementCtor(int field) {
        int i = field;
        this.field = field;
        i++;
        field += 1;
        this.field++;
    }
    // without parameters
    InputXpathParameterPlacementCtor() {
        field = 0;
    }
    InputXpathParameterPlacementCtor(char a, // warn
            int b) {

    }

}
