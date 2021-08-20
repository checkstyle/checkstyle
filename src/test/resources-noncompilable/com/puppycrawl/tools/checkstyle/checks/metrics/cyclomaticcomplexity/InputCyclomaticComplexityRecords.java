/*
CyclomaticComplexity
max = 0
switchBlockAsSingleDecisionPoint = (default)false
tokens = (default)LITERAL_WHILE, LITERAL_DO, LITERAL_FOR, LITERAL_IF, LITERAL_SWITCH, \
         LITERAL_CASE, LITERAL_CATCH, QUESTION, LAND, LOR


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.metrics.cyclomaticcomplexity;

public class InputCyclomaticComplexityRecords {

    public record MyRecord1(String str, Record record) {
        public MyRecord1 (String str) { // violation
            this("my record", new MyRecord3(true, false));
            int a = 1;
            int b = 1;
            int c = 1;
            int d = 1;
            int n = 8;
            if (a == 1) { // 2, if
            } else if (a == b // 3, if
                    && a == c) { // 4, && operator
                if (c == 2) { // 5, if
                }
            } else if (a == d) { // 6, if
                try {
                } catch (Exception e) { // 7, catch
                }
            } else {
                switch (n) {
                    case 1: // 8, case
                        break;
                    case 2: // 9, case
                        break;
                    case 3: // 10, case
                        break;
                    default:
                        break;
                }
            }
            d = a < 0 ? -1 : 1; // 11, ternary operator
        }

        public void foo() { // violation
            int a = 1;
            int b = 1;
            int c = 1;
            int d = 1;
            int n = 8;
            if (a == 1) { // 2, if
            } else if (a == b // 3, if
                    && a == c) { // 4, && operator
                if (c == 2) { // 5, if
                }
            } else if (a == d) { // 6, if
                try {
                } catch (Exception e) { // 7, catch
                }
            } else {
                switch (n) {
                    case 1: // 8, case
                        break;
                    case 2: // 9, case
                        break;
                    case 3: // 10, case
                        break;
                    default:
                        break;
                }
            }
            d = a < 0 ? -1 : 1; // 11, ternary operator
        }
    }

}


record MyRecord2(boolean t, boolean f) {
    public MyRecord2 { // violation
        int a = 1;
        int b = 1;
        int c = 1;
        int d = 1;
        int n = 8;
        if (a == 1) { // 2, if
        } else if (a == b // 3, if
                && a == c) { // 4, && operator
            if (c == 2) { // 5, if
            }
        } else if (a == d) { // 6, if
            try {
            } catch (Exception e) { // 7, catch
            }
        } else {
            switch (n) {
                case 1: // 8, case
                    break;
                case 2: // 9, case
                    break;
                case 3: // 10, case
                    break;
                default:
                    break;
            }
        }
        d = a < 0 ? -1 : 1; // 11, ternary operator
    }
}

record MyRecord3(boolean a, boolean b) {
    MyRecord3(String str) { // violation
        this(true, true);
        int a = 1;
        int b = 1;
        int c = 1;
        int d = 1;
        int n = 8;
        if (a == 1) { // 2, if
        } else if (a == b // 3, if
                && a == c) { // 4, && operator
            if (c == 2) { // 5, if
            }
        } else if (a == d) { // 6, if
            try {
            } catch (Exception e) { // 7, catch
            }
        } else {
            switch (n) {
                case 1: // 8, case
                    break;
                case 2: // 9, case
                    break;
                case 3: // 10, case
                    break;
                default:
                    break;
            }
        }
        d = a < 0 ? -1 : 1; // 11, ternary operator
    }
}

record MyRecord4(boolean a, boolean b) {
    public Record foo() { // violation
        int a = 1;
        int b = 1;
        int c = 1;
        int d = 1;
        int n = 8;
        if (a == 1) { // 2, if
        } else if (a == b // 3, if
                && a == c) { // 4, && operator
            if (c == 2) { // 5, if
            }
        } else if (a == d) { // 6, if
            try {
            } catch (Exception e) { // 7, catch
            }
        } else {
            switch (n) {
                case 1: // 8, case
                    break;
                case 2: // 9, case
                    break;
                case 3: // 10, case
                    break;
                default:
                    break;
            }
        }
        d = a < 0 ? -1 : 1; // 11, ternary operator
        record MyMethodRecord(String str){}
        return new MyMethodRecord("my method record!");

    }
}
