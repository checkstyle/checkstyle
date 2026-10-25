/*
ExpressionOverBlockLambda


*/

package org.checkstyle.suppressionxpathfilter.coding.expressionoverblocklambda;

import java.util.List;

public class InputXpathExpressionOverBlockLambdaMethodCall {
    void testMethod(List<Integer> list) {
        // violation below 'Expression lambdas are preferred over single-line block lambdas.'
        list.forEach(s -> { System.out.println(s); });
    }
}
