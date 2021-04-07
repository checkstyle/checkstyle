package com.sun.checkstyle.test.chapter4indentation.rule41linelength;

public class InputLineLength
{
    int i;
    int j;
    int i1;
    int j1;
    // A very, very long line that is OK because it matches the regexp "^.*is OK.*regexp.*$" // warn
    // tabs that count as one char because of their position ->	<-   ->	<-, OK

    // second definition is wrapped
    // line of VARIABLE_DEF is not the same as first line of the definition
    java.lang.String string; java.lang.String
        strings[];
    //both definitions is wrapped
    java.lang.
        String string1; java.lang.String
            strings1[];

    void method2() {
        for (int i=0, j=0; i < 10; i++, j--) {
        }

        for(int i=0; i<4;i++) {

        }
    }

    class Inner {
        int i, j;
        int i1; int j1;

        void method1() {
            String str, str1;
            java.lang.Object obj; Object obj1;
        }
        // second definition is wrapped
        // line of VARIABLE_DEF is not the same as first line of the definition
        java.lang.String string; java.lang.String
            strings[];
        //both definitions is wrapped
        java.lang.
            String string1; java.lang.String
                strings1[];

        void method2() {
            for (int i=0, j=0; i < 10; i++, j--) {
            }

            for(int i=0; i<4;i++) {

            }
        }
        Inner anon = new Inner()
        {
            int i, j;
            int i1; int j1;

            void method1() {
                String str, str1;
                java.lang.Object obj; Object obj1;
            }
            // second definition is wrapped
            // line of VARIABLE_DEF is not the same as first line of the definition // warn
            java.lang.String string; java.lang.String
                strings[];
            //both definitions is wrapped
            java.lang.
                String string1; java.lang.String
                    strings1[];

            void method2() {
                for (int i=0, j=0; i < 10; i++, j--) {
                }

                for(int i=0; i<4;i++) {

                }
            }
        };
    }
}

class Suppress {
    @SuppressWarnings("unused")
    long q1, q2, q3;

    @SuppressWarnings("unused") long q4, q5, q6;
}
