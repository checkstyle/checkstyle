/*
RightCurly
option = ALONE
tokens = LITERAL_SWITCH

*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

import java.io.IOException;

public class InputRightCurlyTestSwitchExpression5 {
    public class InputFinalLocalVariableCheckSwitchAssignment {
        private static final int staticValue = 2;

        public InputFinalLocalVariableCheckSwitchAssignment() throws Exception {
            int a = 0;
            final int b = 0;
            int c = 10;
            int d = 0;

            c = switch (a) {
                case 0 -> 5;
                case 1 -> 10;
                default -> switch (b) {
                    case 2 -> {
                        if (a == 0) {
                            d = 1; // reassign d
                        }
                        throw new Exception();
                    }
                    default -> 2;
                };
            };
        }

        public void foo() throws Exception {
            final int a = 0;

            int b = switch (a) {
                case 0 -> {
                    int x = 5;
                    int y = 6;
                    if (a == 2) {
                        y = 7;
                    }
                    throw new Exception(); }
                default -> 2; };

            int c = switch (b) {
                case 0 -> 1;
                default -> 2;
            };

            c = switch (a) {
                case 0 -> switch (b) {
                    case 0 -> 1;
                    case 1 -> 2;
                    default -> 3; };
                default -> 1; };
        }
    }
}

class InputMissingSwitchDefaultCheckSwitchExpressionsThree {
    public enum Day {
        SUN,
    }
    public enum Options { THREE }

    public void foo2(Options option) {
        assert Integer.valueOf(1).equals(switch (option) {
            case THREE -> 3;
        });
    }
}

class InputExecutableStatementCountRecords {

    private int id(int i) {
        return i;
    }

    private final int value = 2;

    private final int field = id(switch (value) {
        case 0 -> -1;
        case 2 -> {
            int temp = 0;
            temp += 3;
            yield temp;
        }
        default -> throw new IllegalStateException();
    });

    public static void method0() throws IOException {
        int stValue = 0;
        if (stValue > 0) {
            try {
                switch (stValue) {
                    case 0:
                        break;
                }
            } catch (NumberFormatException e) {
                throw new IOException(" value ");
            }
        }
    }
}
