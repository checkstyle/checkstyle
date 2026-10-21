/*
ParameterAssignment


*/

package org.checkstyle.suppressionxpathfilter.coding.parameterassignment;

public class InputXpathParameterAssignmentLambdas {

    public interface SomeInterface {
        void method(int a);
    }

    SomeInterface obj1 = q -> q++; // violation "Assignment of parameter 'q' is not allowed."
    void method() {
        int q = 12;
        SomeInterface obj = (d) -> {
            SomeInterface b = (c) -> obj1.equals(this);
            int c = 12;
            c++;
        };
    }
}
