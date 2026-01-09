/*
OneStatementPerLine
treatTryResourcesAsStatement = (default)false


*/
package com.puppycrawl.tools.checkstyle.checks.coding.onestatementperline;

public class InputOneStatementPerLineSemiBlockStatement {

    int a = 1; static class Inner {
    } int a2 = 1;
    // violation 2 lines above 'Only one statement per line allowed.'
    // violation 2 lines above 'Only one statement per line allowed.'

    int
       b = 1; record Inner2() {
    } int b2 = 1;
    // violation 2 lines above 'Only one statement per line allowed.'
    // violation 2 lines above 'Only one statement per line allowed.'

    int c
          = 1; interface Inner3 {} int
     c3 = 1; // 2 violations above:
    // 'Only one statement per line allowed.'
    // 'Only one statement per line allowed.'

    // violation below 'Only one statement per line allowed.'
    int d = 1; enum Inner4
    {
    } int d2 = 1;
    // violation above 'Only one statement per line allowed.'

    int e = 1; @interface
    MyAnnotation {
    } int e2 = 1;
    // violation 3 lines above 'Only one statement per line allowed.'
    // violation 2 lines above 'Only one statement per line allowed.'

    class A {
        int k = 1;
    } class B {
        int k2 = 1;
    }
    // violation 3 lines above 'Only one statement per line allowed.'

    void method() {
        class C{}
    }
}
