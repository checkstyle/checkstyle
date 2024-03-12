/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = (default)true
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, CLASS_DEF, INTERFACE_DEF, ENUM_DEF, \
         STATIC_INIT, INSTANCE_INIT, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, \
         COMPACT_CTOR_DEF


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator; // violation ''package' should be separated from previous line.'

public class InputEmptyLineSeparatorRecordsAndCompactCtors {
    record InBetween1(int x, int y) {}
    record MyRecord1(String foo) { // violation ''RECORD_DEF' .* separated from previous line.'
        public static final int FOO_CONST = 1;
        public void method() {} // violation ''METHOD_DEF' .* separated from previous line.'
        public MyRecord1{} // violation ''COMPACT_CTOR_DEF' .* separated from previous line.'
        MyRecord1(int x){this("string");} // violation ''CTOR_DEF' .* separated .* previous line.'
        static{} // violation ''STATIC_INIT' should be separated from previous line.'
    }
    record InBetween2(int x, int y) {} // violation ''RECORD_DEF' .* separated from previous line.'
    class MyClass1 { // violation ''CLASS_DEF' should be separated from previous line.'
        public static final int FOO_CONST = 1;
        public void method() {} // violation ''METHOD_DEF' should be separated from previous line.'
        public MyClass1(){} // violation ''CTOR_DEF' should be separated from previous line.'
        MyClass1(String foo){} // violation ''CTOR_DEF' should be separated from previous line.'
    }

    record recordCompactCtors1(){
        void method1(){}
        public recordCompactCtors1{} // violation ''COMPACT_CTOR_DEF'.*separated .*previous line.'
        void method2(){} // violation ''METHOD_DEF' should be separated from previous line.'
    }

    record recordCompactCtors2(){
        public static final int X = 1;
        public recordCompactCtors2{} // violation ''COMPACT_CTOR_DEF'.*separated .*previous line.'
        public static final int Y = 1; // violation ''VARIABLE_DEF' .*separated .*previous line.'
    }

    record MyRecord2(String foo) {

        public static final int FOO_CONST = 1;

        public void method() {}

        public MyRecord2{}

        MyRecord2(int x){this("string");}

        static{}
    }

    record InBetween3(int x, int y) {}

    class MyClass2 {

        public static final int FOO_CONST = 1;

        public void method() {}

        public MyClass2(){}

        MyClass2(String foo){}
    }
}
