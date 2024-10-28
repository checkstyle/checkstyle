package org.checkstyle.suppressionxpathfilter.parameterplacement;

public class InputXpathParameterPlacementMethods {
    int field;
    void Test1(int field) {
        int i = field;
        this.field = field;
        i++;
        field += 1;
        this.field++;
    }
    // without parameters
    void Test2() {
        field = 0;
    }
    void myMethod2(int a, // warn
        long b) {
    }
}
