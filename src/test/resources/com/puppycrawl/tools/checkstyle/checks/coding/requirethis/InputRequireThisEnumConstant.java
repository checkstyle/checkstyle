package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisEnumConstant {
    private final String TEST = "";

    public enum TestEnum {
        TEST;

        public TestEnum method() {
            return TEST;
        }
    }
}
