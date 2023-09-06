package com.sun.checkstyle.test.chapter6declarations.rule61numberperline;

public class InputMultipleVariableDeclarations
{
    int i, j; //warn
    int i1; int j1; //warn

    void method1() {
        String str, str1; //warn
        java.lang.Object obj; Object obj1; //warn
    }
    // second definition is wrapped
    // line of VARIABLE_DEF is not the same as first line of the definition
    java.lang.String string; java.lang.String //warn
        strings[];
    //both definitions are wrapped
    java.lang. //warn
        String string1; java.lang.String
            strings1[];

    void method2() {
        for (int i=0, j=0; i < 10; i++, j--) { //ok
        }

        for(int i=0; i<4;i++) {

        }
    }

    class Inner {
        int i, j; //warn
        int i1; int j1; //warn

        void method1() {
            String str, str1; //warn
            java.lang.Object obj; Object obj1; //warn
        }
        // second definition is wrapped
        // line of VARIABLE_DEF is not the same as first line of the definition
        java.lang.String string; java.lang.String //warn
            strings[];
        //both definitions are wrapped
        java.lang. //warn
            String string1; java.lang.String
                strings1[];

        void method2() {
            for (int i=0, j=0; i < 10; i++, j--) { //ok
            }

            for(int i=0; i<4;i++) {

            }
        }
        Inner anon = new Inner()
        {
            int i, j; //warn
            int i1; int j1; //warn

            void method1() {
                String str, str1; //warn
                java.lang.Object obj; Object obj1; //warn
            }
            // second definition is wrapped
            // line of VARIABLE_DEF is not the same as first line of the definition
            java.lang.String string; java.lang.String //warn
                strings[];
            //both definitions are wrapped
            java.lang. //warn
                String string1; java.lang.String
                    strings1[];

            void method2() {
                for (int i=0, j=0; i < 10; i++, j--) { //ok
                }

                for(int i=0; i<4;i++) {

                }
            }
        };
    }
}

class Suppress {
    @SuppressWarnings("unused") //warn
    long q1, q2, q3;

    @SuppressWarnings("unused") long q4, q5, q6;  //warn
}
