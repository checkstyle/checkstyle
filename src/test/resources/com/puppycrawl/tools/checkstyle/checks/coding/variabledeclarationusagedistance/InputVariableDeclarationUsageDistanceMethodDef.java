/*
VariableDeclarationUsageDistance
allowedDistance = 1
ignoreFinal = false
validateBetweenScopes = true
ignoreVariablePattern = (default)


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

import java.util.Iterator;

/**
 * Test case, method definition tokens should not count towards distance.
 */
public class InputVariableDeclarationUsageDistanceMethodDef {

    public void testMethodWithGenericInnerClass() {
        int a = 1;
        class GeneralLogic {
            public <T> Iterator<T> method(T[] b) throws Exception {
                if (a > 0) {
                    System.out.println(b.length);
                }
                return null;
            }
        }
    }

    public void testConstructorInInnerClass() {
        String value = "test";
        class Container {
            Container(String param) throws IllegalArgumentException {
                System.out.println(value + param);
            }
        }
    }
}
