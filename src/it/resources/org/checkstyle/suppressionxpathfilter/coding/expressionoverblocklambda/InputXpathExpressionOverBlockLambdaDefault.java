/*
ExpressionOverBlockLambda


*/

package org.checkstyle.suppressionxpathfilter.coding.expressionoverblocklambda;

public class InputXpathExpressionOverBlockLambdaDefault {
    // violation below 'Expression lambdas are preferred over single-line block lambdas.'
    Runnable a = () -> { System.out.println("hello"); };
}
