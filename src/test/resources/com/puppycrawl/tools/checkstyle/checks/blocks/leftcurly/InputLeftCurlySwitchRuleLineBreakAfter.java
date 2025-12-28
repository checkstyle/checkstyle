/*
LeftCurly
option = (default)eol
ignoreEnums = false
tokens = (default)ANNOTATION_DEF, CLASS_DEF, CTOR_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, INTERFACE_DEF, LAMBDA, LITERAL_CASE, LITERAL_CATCH, \
         LITERAL_DEFAULT, LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY, LITERAL_FOR, \
         LITERAL_IF, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_TRY, LITERAL_WHILE, \
         METHOD_DEF, OBJBLOCK, STATIC_INIT, RECORD_DEF, COMPACT_CTOR_DEF, SWITCH_RULE


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

/** Some javadoc. */
public class InputLeftCurlySwitchRuleLineBreakAfter {

    void testMethod1(int val) {
        int result =
                switch (val) {
                    case 1, 6, 7 -> {
                        System.out.println("try");
                        yield val*2;
                    }
                    // violation below ''{' at column 31 should have line break after'
                    case 2 -> { yield 2; }
                    // violation below ''{' at column 31 should have line break after'
                    case 3 -> { yield 4; }
                    default -> 0;
                };
    }

    void testMethod2(int number) {
        switch (number) {
            case 0, 1 -> System.out.println("0");
            case 2 ->
                    handleTwoWithAnExtromelyLongMethodCallThatWouldNotFitOnTheSameLine();
            // violation below ''{' at column 24 should have line break after'
            default -> { handleSurprisingNumber(number); }
        }
    }

    void testValidCases(int x) {
        switch (x) {
            case 1 -> {
                System.out.println("one");
            }
            case 2 -> System.out.println("two");
            case 3 -> { }
            default -> {
                System.out.println("default");
            }
        }
    }

    private void handleSurprisingNumber(int number) {
        // do nothing
    }

    private int handleTwoWithAnExtromelyLongMethodCallThatWouldNotFitOnTheSameLine() {
        return 0;
    }

    static {
        int x = 1;
    }

    void testValidEolNoViolation(int x) {
        switch (x) {
            case 9 -> {
                System.out.println("valid");
            }
        }
    }

    void testOldStyleSwitch(int x) {
        switch (x) {
            case 10: {
                System.out.println("old style");
            }
            break;
        }
    }

}

enum TestEnumCoverage {
    A
      { // violation ''{' at column 7 should be on the previous line'
        void method() { }
      };
}
