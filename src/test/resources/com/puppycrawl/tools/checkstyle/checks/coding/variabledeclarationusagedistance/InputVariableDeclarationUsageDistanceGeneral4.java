/*
VariableDeclarationUsageDistance
allowedDistance = 1
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class InputVariableDeclarationUsageDistanceGeneral4 {

    class Parent {
        void mm() {
        }
        <T> void xx(List<T> m){}
    }
    public void method9() {
        Integer z = 5; // violation 'Distance .* is 2.'
        Iterator<Integer> mn = new HashSet<Integer>().iterator();
        class BClass extends Parent {
            Boolean h = false;
            @Override
            public <t> void xx(List<t> a) {
                if (a.get(0).toString().compareTo("1") > 0) {
                    System.out.println("temp");
                }
            }
            @Override
            public void mm() {
            }
            void nn(Boolean m) {
                if(z<0){
                    System.out.println();
                }
            }
        }
    }
}
