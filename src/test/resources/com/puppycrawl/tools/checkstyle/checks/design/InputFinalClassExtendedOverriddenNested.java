package com.puppycrawl.tools.checkstyle.checks.design;

public class InputFinalClassExtendedOverriddenNested {
    private class Test {
        private Test() {
        }
    }

    private class Test2 extends Test {
    }
}
