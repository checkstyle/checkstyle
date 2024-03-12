/*
RightCurly
option = ALONE
tokens = LITERAL_SWITCH

*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyTestSwitchExpression4 {

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
                    throw new Exception();
                }
                default -> 2;
            };

            int c = switch (b) {
                case 0 -> 1;
                default -> 2;
            };

            c = switch (a) {
                case 0 -> switch (b) {
                    case 0 -> 1;
                    case 1 -> 2;
                    default -> 3;
                };
                default -> 1;
            };
        }
    }
}

class InputMissingSwitchDefaultCheckSwitchExpressionsThree {
    public enum Options {
        ONE,
        TWO,
        THREE
    }

    public enum Day {
        SUN,
    }

    public void foo2(Options option) {
        assert Integer.valueOf(1).equals(switch (option) {
            case ONE -> 1;
            case TWO -> 2;
            case THREE -> 3;
        });
    }
}

class InputExecutableStatementCountRecords {

      private int id(int i) {
          return i;
      }

      private final int value = 2;

      private final int field = id(switch(value) {
          case 0 -> -1;
          case 2 -> {
              int temp = 0;
              temp += 3;
              yield temp;
          }
          default -> throw new IllegalStateException();
      });

    void commentBeforeRightCurly() {
        int i = 20;
        while (true) {
            switch (i) {
                case 0:
                    i++;
            }
            switch (i) {
                case 0:
                    i++;
            /* fallthru */ } // violation ''}' at column 28 should be alone on a line'
        }
    }
}
