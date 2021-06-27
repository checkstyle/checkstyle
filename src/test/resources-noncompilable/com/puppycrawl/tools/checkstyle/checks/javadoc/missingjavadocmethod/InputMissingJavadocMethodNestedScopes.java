//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

/*
 * Config:
 * scope = public
 */
public class InputMissingJavadocMethodNestedScopes {
    interface SubClass {
        static void publicFoo1() {} // ok
        private static void foo1() {} // ok
    }

    public interface SubClass1 {
        static void publicPoo2() {} // violation
        private static void foo2() {} // violation
    }

    protected interface SubClass2{
        static void publicFoo3() {} // ok
        private static void foo3() {} // ok
    }

    private interface SubClass3 {
        static void publicFoo4() {} // ok
        private static void foo4() {} // ok
    }

    public interface TestClass {
        void method1();

        class Class1 {
            public void method2(){}
            public interface PublicInterface {
                void method3();
                class Class2 {
                    public void method4(){}
                }
            }
            interface PackageInterface {
                void method5();
                class Class2 {
                    public void method6(){}
                }
            }
        }
    }
}

class PackageClassTwo {
    interface SubClass {
        static void publicFoo1() {} // ok
        private static void foo1() {} // ok
    }

    public interface SubClass1 {
        static void publicFoo2() {} // ok
        private static void foo2() {} // ok
    }

    protected interface SubClass2{
        static void publicFoo3() {} // ok
        private static void foo3() {} // ok
    }

    private interface SubClass3 {
        static void publicFoo4() {} // ok
        private static void foo4() {} // ok
    }
}
