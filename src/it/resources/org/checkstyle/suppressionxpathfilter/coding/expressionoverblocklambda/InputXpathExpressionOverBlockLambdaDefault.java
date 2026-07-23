package org.checkstyle.suppressionxpathfilter.coding.expressionoverblocklambda;

public class InputXpathExpressionOverBlockLambdaDefault {
    Runnable a = () -> { System.out.println("hello"); }; //warn
}
