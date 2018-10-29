package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

class InputFinalLocalVariableNameShadowing {
    public void foo(String text) {
        System.identityHashCode(text);

        class Bar {
            void bar (String text) {
                text = "xxx";
            }
        }
    }
}

class Foo2 {
    public void foo() {
        int x;
        class Bar {
            void bar () {
                int x = 1;
                x++;
                x++;
            }
        }
    }
}

enum InputFinalLocalVariableNameShadowingEnum{
    test;
    final String foo1 = "error";
    InputFinalLocalVariableNameShadowingEnum()
    {
        String foo = foo1;
        foo += foo1;
    }

}
