/*
VariableDeclarationUsageDistance
allowedDistance = 1
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = false


*/


package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

import java.util.Iterator;

public class InputVariableDeclarationUsageDistanceMethodDefinition {
    public void noIssueTest(){
        int a=1; // ok
        class GeneralLogic {
            public <T> Iterator<T> method(T[] b) throws Exception{
                if(a>0){
                    System.out.println(b.length);
                }
                return null;
            }
        }
    }
    public void oneIssueTest() {
        int a = 1; // violation 'Distance .* is 2.'
        class LogicThrowViolation2 {
            int d = 3;
            int c = 4;

            public <T> Iterator<T> method(T[] b) throws Exception {
                d = c + 1;
                System.out.println(d);
                if (a > 0) {
                    System.out.println(b.length);
                }
                return null;
            }
        }
    }
}
