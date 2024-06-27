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
        <T> void xx(List<T> m){}
    }

    public void method2() {
        Integer a = 5;
        class AClass extends Parent {
            int i;

            public AClass(int i) {
                this.i = i;
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

    public interface Ani {
        public void method4(Object o);
    }

    public void method5() {
        Object a = new Object();
        class AClass implements Ani {
            @Override
            public void method4(Object o) {
                if (a.toString().isEmpty()) {
                    System.out.println(o.toString());
                }
            }
        }
    }

    public void method6() {
        String b = ""; // violation 'Distance .* is 3, but allowed 1.'
        List<Integer> numbers = new ArrayList<>();
        numbers.add(5);
        numbers.add(9);
        numbers.forEach((n) -> {
            try (AutoCloseable j = new java.io.StringReader("");
                 final AutoCloseable k = new java.io.StringReader(b);) {
                String c = b.toString();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void method7() {
        // until https://github.com/checkstyle/checkstyle/issues/13011, the below distance is 3
        Integer t = 5;
        nothing();
        System.out.println();
        class BClass extends Parent {
            @Override
            void mm() {
                System.out.println(t);
            }
        }
    }

    public void method8() {
        Integer m = 10;
        class BClass extends Parent{
            public BClass getNewInstance(){
                return new BClass(){
                    private final int t = m;

                    @Override
                    void mm(){
                        System.out.println();
                    }
                };
            }
        }
    }
}
