/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis; // ok

public class InputRequireThisEnumConstant {
    private final String TEST = "";

    public enum TestEnum {
        TEST;

        public TestEnum method() {
            return TEST;
        }
    }
}
