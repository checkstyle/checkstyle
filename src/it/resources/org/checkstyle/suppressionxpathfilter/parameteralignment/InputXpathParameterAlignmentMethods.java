package org.checkstyle.suppressionxpathfilter.parameteralignment;

public class InputXpathParameterAlignmentMethods {
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
    void myMethod2(int a,
        long b) { // warn
    }
}
