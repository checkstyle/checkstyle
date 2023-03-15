/*
MultipleVariableDeclarations


*/

package com.puppycrawl.tools.checkstyle.checks.coding.multiplevariabledeclarations;

public class InputMultipleVariableDeclarations
{
    int i, j; // violation 'Each variable declaration must be in its own statement.'
    int i1; int j1; // violation 'Only one variable definition per line allowed.'

    void method1() {
        String str, str1; // violation 'Each variable declaration must be in its own statement.'
        Object obj; Object obj1; // violation 'Only one variable definition per line allowed.'
    }
    // second definition is wrapped
    // line of VARIABLE_DEF is not the same as first line of the definition
    java.lang.String string; String // violation 'Only one variable definition per line allowed.'
        strings[];
    //both definitions are wrapped
    java.lang. // violation 'Only one variable definition per line allowed.'
        String string1; java.lang.String
            strings1[];

    void method2() {
        for (int i=0, j=0; i < 10; i++, j--) {
        }

        for(int i=0; i<4;i++) {

        }

        switch("") {
        case "6":
            int k = 7;
        }
    }

    void method3() {
        java.lang.Object obj; Object obj1; Object obj2; Object obj3; // 3 violations
        for (String s : new String[] {}) {}
    }
}
