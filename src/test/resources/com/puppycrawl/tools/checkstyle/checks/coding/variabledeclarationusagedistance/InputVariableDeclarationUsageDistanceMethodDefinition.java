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
    public void method(){
        int a=1;
        int b=2;
        int c=3;// violation 'Distance .* is 2.'
        class GeneralLogic {
            public <T> Iterator<T> method(T[] b) throws Exception{
                if(a>0){
                    System.out.println(b.length);
                }
                return null;
            }
        };
        System.out.print(b);
        System.out.print(c);
    }
}
