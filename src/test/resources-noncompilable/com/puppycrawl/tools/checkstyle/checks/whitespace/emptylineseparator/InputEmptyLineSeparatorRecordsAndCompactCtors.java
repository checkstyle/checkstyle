//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator; // violation

/* Config:
 * allowNoEmptyLineBetweenFields = false
 * allowMultipleEmptyLines = true
 * allowMultipleEmptyLinesInsideClassMembers = true
 * tokens = {PACKAGE_DEF , IMPORT , STATIC_IMPORT , CLASS_DEF , INTERFACE_DEF , ENUM_DEF ,
 *  STATIC_INIT , INSTANCE_INIT , METHOD_DEF , CTOR_DEF , VARIABLE_DEF,
 *  RECORD_DEF, COMPACT_CTOR_DEF}
 *
 */
public class InputEmptyLineSeparatorRecordsAndCompactCtors {
    record InBetween1(int x, int y) {}
    record MyRecord1(String foo) { // violation
        public static final int FOO_CONST = 1;
        public void method() {} // violation
        public MyRecord1{} // violation
        MyRecord1(int x){this("string");} // violation
        static{} // violation
    }
    record InBetween2(int x, int y) {} // violation
    class MyClass1 { // violation
        public static final int FOO_CONST = 1;
        public void method() {} // violation
        public MyClass1(){} // violation
        MyClass1(String foo){} // violation
    }

    record recordCompactCtors1(){
        void method1(){}
        public recordCompactCtors1{} // violation
        void method2(){} // violation
    }

    record recordCompactCtors2(){
        public static final int X = 1;
        public recordCompactCtors2{} // violation
        public static final int Y = 1; // violation
    }

    record MyRecord2(String foo) { // ok

        public static final int FOO_CONST = 1;

        public void method() {} // ok

        public MyRecord2{} // ok

        MyRecord2(int x){this("string");} // ok

        static{} // ok
    }

    record InBetween3(int x, int y) {} // ok

    class MyClass2 {

        public static final int FOO_CONST = 1;

        public void method() {} // ok

        public MyClass2(){} // ok

        MyClass2(String foo){} // ok
    }
}
