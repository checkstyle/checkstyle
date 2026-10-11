package org.checkstyle.suppressionxpathfilter.coding.expressionoverblocklambda;

import java.util.List;

public class InputXpathExpressionOverBlockLambdaMethodCall {
    void testMethod(List<Integer> list) {
        list.forEach(s -> { System.out.println(s); }); // warn
    }
}
