/*
OneStatementPerLine
treatTryResourcesAsStatement = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.onestatementperline;

public class InputOneStatementPerLineAnnotation1 {

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

}
