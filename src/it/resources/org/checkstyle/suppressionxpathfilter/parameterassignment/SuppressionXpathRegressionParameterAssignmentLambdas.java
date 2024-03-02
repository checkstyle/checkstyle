package com.puppycrawl.tools.checkstyle.checks.coding.parameterassignment;

public class SuppressionXpathRegressionParameterAssignmentLambdas {

     public interface SomeInterface {
        void method(int a);
    }

    SomeInterface obj1 = q -> q++; // warn
    SomeInterface obj2 = (int q) -> q += 12; // warn
    SomeInterface obj3 = (w) -> w--; // warn
}
