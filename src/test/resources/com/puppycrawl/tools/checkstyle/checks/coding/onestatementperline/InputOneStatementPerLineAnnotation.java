/*
OneStatementPerLine
treatTryResourcesAsStatement = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.onestatementperline;

import java.io.IOException;
import java.io.StringReader;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.function.Supplier;

public class InputOneStatementPerLineAnnotation {

    @Deprecated
    String field1 = "1";
    String field2 = "2";

    @Deprecated String field3 = "3";
    String field4 = "4";

    @Deprecated
    @SuppressWarnings("unused")
    private static final String FIELD5 = "5";
    String field6 = "6";

    @SuppressWarnings({
        "unused",
        "deprecation",
    })
    String field7 = "7";
    String field8 = "8";

    @Deprecated String field9 = "9"; String field10 = "10";
    // violation above 'Only one statement per line allowed.'

    @Deprecated
    String field11 = "11"; String field12 = "12";
    // violation above 'Only one statement per line allowed.'

    String field13 = "13"; @Deprecated String field14 = "14";
    // violation above 'Only one statement per line allowed.'

    @Deprecated String field15 = "15"; @Deprecated String field16 = "16";
    // violation above 'Only one statement per line allowed.'

    @Deprecated
    void method1() {
    }
    String field17 = "17";

    void method2() {
        @SuppressWarnings("unused")
        String local1 = "1";
        String local2 = "2";

        @SuppressWarnings("unused") String local3 = "3";
        String local4 = "4";

        final @SuppressWarnings("unused") String local5 = "5";
        String local6 = "6";

        @SuppressWarnings("unused")
        final String local7 = "7";
        String local8 = "8";
    }

    void method3() {
        @SuppressWarnings("unused") String local1 = "1"; String local2 = "2";
        // violation above 'Only one statement per line allowed.'

        @SuppressWarnings("unused")
        String local3 = "3"; String local4 = "4";
        // violation above 'Only one statement per line allowed.'

        String local5 = "5"; @SuppressWarnings("unused") String local6 = "6";
        // violation above 'Only one statement per line allowed.'

        int counter = 0;
        @SuppressWarnings("unused") String local7 = "7"; counter++;
        // violation above 'Only one statement per line allowed.'
    }

    void method4() {
        @Deprecated
        class LocalClass {
        }
        String local1 = "1";

        String local2 = "2"; @Deprecated class LocalClass2 { }
        // violation above 'Only one statement per line allowed.'
    }

    void method5() {
        for (@SuppressWarnings("unused") int i = 0; i < 1; i++) {
            @SuppressWarnings("unused")
            int j = i;
        }

        Supplier<String> supplier = () -> {
            @SuppressWarnings("unused")
            String local1 = "1";
            String local2 = "2";
            @SuppressWarnings("unused") String local3 = "3"; return local2;
            // violation above 'Only one statement per line allowed.'
        };

        Object anonymous = new Object() {
            @Deprecated
            String inner1 = "1";
            String inner2 = "2";
            @Deprecated String inner3 = "3"; String inner4 = "4";
            // violation above 'Only one statement per line allowed.'
        };
    }

    void method6() throws IOException {
        try (@SuppressWarnings("unused") StringReader reader1 = new StringReader("1");
             @SuppressWarnings("unused") StringReader reader2 = new StringReader("2")) {
            @SuppressWarnings("unused")
            String local1 = "1";
        }
    }

    void method7() {
        java.lang.@TypeAnnotation String local1 = "1";
        String local2 = "2";

        java.lang.@TypeAnnotation String local3 = "3"; String local4 = "4";
        // violation above 'Only one statement per line allowed.'
    }

    interface InnerInterface {
        @Deprecated
        void method1();
        void method2();

        @Deprecated void method3(); void method4();
        // violation above 'Only one statement per line allowed.'
    }

    @interface InnerAnnotation {
        @Deprecated
        String value1();
        String value2();

        @Deprecated String value3(); String value4();
        // violation above 'Only one statement per line allowed.'
    }

    @Target(ElementType.TYPE_USE)
    @interface TypeAnnotation {
    }

}
