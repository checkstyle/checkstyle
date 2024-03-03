package org.checkstyle.suppressionxpathfilter.parameterassignment;

public class SuppressionXpathRegressionParameterAssignmentLambdas {

     public interface SomeInterface {
        void method(int a);
    }

    SomeInterface obj1 = q -> q++; // warn
    void method() {
        int q = 12;
        SomeInterface obj = (d) -> {
            SomeInterface b = (c) -> obj1.equals(this); // ok
            int c = 12;
            c++;
        };
    }
}
