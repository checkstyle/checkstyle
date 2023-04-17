/*
VariableDeclarationUsageDistance
allowedDistance = 1
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

import java.util.ArrayList;
import java.util.List;

public class InputVariableDeclarationUsageDistanceGeneral3 {

    void nothing() {
    }

    class Parent {
        void mm() {
        }
    }

    public void method2() {
        // false positive, the distance is 1
        Integer a = 5;  // violation 'Distance .* is 4.'
        class AClass extends Parent {
            int i;
            public AClass(int i){
                this.i=i;
            }
            @Override
            void mm() {
                if (a >= 0) {
                    System.out.println("test");
                }
            }
        }
    }

    public void method3() {
        Integer a = 5; // violation 'Distance .* is 2.'
        nothing();
        InputVariableDeclarationUsageDistanceGeneral3 m =
                new InputVariableDeclarationUsageDistanceGeneral3() {
                    @Override
                    public void method2() {
                        if (a >= 0) {
                            System.out.println("new test");
                        }
                    }
                };
    }

    public interface Ani{
        public void method4(Object o);
    }

    public void method5(){
        // false positive, the distance is 1
        Object a = new Object(); // violation 'Distance .* is 4.'
        class AClass implements Ani {
            @Override
            public void method4(Object o) {
                if(a.toString().isEmpty()){
                    System.out.println(o.toString());
                }
            }
        }
    }

    public void method6(){
        String b = "";
        List<Integer> numbers = new ArrayList<>();
        numbers.add(5);
        numbers.add(9);
        numbers.forEach((n)->{
            try (AutoCloseable j = new java.io.StringReader("");
                 final AutoCloseable k = new java.io.StringReader(b);) {
                String c = b.toString();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
