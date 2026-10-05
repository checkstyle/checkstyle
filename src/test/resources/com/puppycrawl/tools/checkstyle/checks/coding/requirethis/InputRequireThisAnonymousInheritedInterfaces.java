/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

import java.util.ArrayList;

public class InputRequireThisAnonymousInheritedInterfaces {

    int deep;
    int constant;
    int second;

    void outerMethod() { }

    void diamond() {
        new Diamond() {
            void test() {
                System.out.println(deep + constant);
                outerMethod();
            }
        };
    }

    void multipleInterfaces() {
        new Impl() {
            void test() {
                System.out.println(second);
            }
        };
    }

    interface First {
        int first = 1;
    }

    interface Second {
        int second = 2;
    }

    static class Impl implements First, Second {
    }

    interface Left extends Right, Top {
    }

    interface Right extends Top {
    }

    interface Top {
        int constant = 1;
        default void outerMethod() { }
    }

    interface Diamond extends Left, Right {
        int deep = 2;
    }
}
