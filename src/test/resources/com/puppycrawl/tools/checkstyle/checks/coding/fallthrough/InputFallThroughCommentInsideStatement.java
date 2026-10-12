/*
FallThrough
checkLastCaseGroup = (default)false
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/

package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThroughCommentInsideStatement {

    int tryBody(int i) {
        switch (i) {
            case 1:
                try // comment
                { return 1; }
                catch (IllegalStateException exc) { return 2; }
            case 2:
                try // comment
                { i++; }
                catch (IllegalStateException exc) { return 2; }
            case 3: // violation 'Fall through from previous branch of the switch statement.'
                return 3;
            default:
                return 4;
        }
    }

    int nextCatch(int i) {
        switch (i) {
            case 1:
                try { return 1; }
                catch (IllegalStateException exc) { return 2; }
                // comment
                catch (RuntimeException exc) { i++; }
            case 2: // violation 'Fall through from previous branch of the switch statement.'
                try { return 1; }
                catch (IllegalStateException exc) { return 2; }
                // comment
                catch (RuntimeException exc) { return 3; }
            case 3:
                return 3;
            default:
                return 4;
        }
    }

    int whileBody(int i) {
        switch (i) {
            case 1:
                while (true) /* comment */ { break; }
            case 2: // violation 'Fall through from previous branch of the switch statement.'
                while (true) /* comment */ { i++; }
            case 3:
                return 3;
            default:
                return 4;
        }
    }

    int doWhileBody(int i) {
        switch (i) {
            case 1:
                do { return 1; } /* comment */ while (i > 0);
            case 2:
                do { i++; } /* comment */ while (i > 0);
            case 3: // violation 'Fall through from previous branch of the switch statement.'
                return 3;
            default:
                return 4;
        }
    }

    int nestedSwitch(int i, int j) {
        switch (i) {
            case 1:
                switch (j) {
                    case 1: return 1;
                    default: return 2;
                    // comment
                }
            case 2:
                switch (j) {
                    case 1: return 1;
                    default: i++;
                    // comment
                }
            case 3: // violation 'Fall through from previous branch of the switch statement.'
                return 3;
            default:
                return 4;
        }
    }
}
