/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisAnonymousInnerClass {
    int a = 12;

    void method() {
        int a = 1;
        InputRequireThisAnonymousInnerClass obj =
            new InputRequireThisAnonymousInnerClass() {
                void method() {
                    a += 1;
                }
            };
    }

    void anotherMethod() {
        int var1 = 12;
        int var2 = 13;
        Foo obj = new Foo() {
            void method() {
                var2 += var1;
            }
        };
        obj.getClass();
    }
}

class SharkFoo {
    int var1 = 12;
}

class Foo {
    int var2 = 13;

    class SharkFoo {
    }
}
