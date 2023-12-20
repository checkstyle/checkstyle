/*
UnusedLocalVariable


*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

import java.util.function.Function;

public class InputUnusedLocalVariableGenericAnonInnerClasses {

    Function<Integer, Integer> obj = a -> {
        int l = 12; // violation
        testClass obj = new testClass() { // violation
            void method() {
                l += 1;
            }
        };
        return a;
    };

    testClass obj2 = new testClass() {
        int l = 13; // ok, instance var
        testClass obj2 = new testClass() { // ok, instance var
            void method() {
                l += 1;
            }
        };
    };

    testClass obj3 = new testClass() {
        void method() {
            int l = 13; // violation
            testClass obj2 = new testClass() { // violation
                void method() {
                    l += 1;
                }
            };
        }
    };
}

class testClass {
    int l = 12;

    void method2() {
        int s = 12; // violation
        int j = 13;
        hoo k = () -> {
            Integer.valueOf(j);
            hoo obj2 = () -> {
                testClass obj3 = new testClass() {
                    void foo() {
                        s += 12;
                    }
                };
                obj3.getClass();
            };
            obj2.anotherMethod();
        };
        k.anotherMethod();
    }

    int s = 2;

    void test() {
        int variable = 1; // violation
        int mainVar = 2;
        int anotherVar = 1; // violation
        testingGenerics<Integer>.nested obj
                = new testingGenerics<Integer>().new nested() {
            void method() {
                anotherVar += 1;
            }
        };
        obj.getClass();

        testingGenerics<Integer>.clazz<String>.nested obj2 // violation
                = new testingGenerics<Integer>().new clazz<String>().new nested() {
            void method() {
                variable += 1;
                Integer.valueOf(mainVar);
            }
        };
    }
}
class testingGenerics<T> {
    int mainVar = 2;

    class clazz<T> {
        class nested {
            int variable = 1;
        }
    }

    class nested {
        int anotherVar = 2;
    }
}
