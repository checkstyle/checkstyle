/*
MultipleVariableDeclarations

*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.multiplevariabledeclarations;

public class InputMultipleVariableDeclarationsUnnamedVariables {
    void test() {
        // violation below, 'Each variable declaration must be in its own statement'
        int _ = sideEffect(), _ = sideEffect();
        // violation below, 'Each variable declaration must be in its own statement'
        int _ = sideEffect(), a = sideEffect();
        // violation below, 'Only one variable definition per line allowed'
        int _ = sideEffect(); int _ = sideEffect();
        // violation below, 'Only one variable definition per line allowed'
        int _ = sideEffect(); int b = sideEffect();
    }

    int sideEffect() {
        return 0;
    }
}
