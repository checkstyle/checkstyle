/*
FallThrough
checkLastCaseGroup = (default)false
reliefPattern = Continue with next case


*/

package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThroughOwnPatternSynchronizedAndOneLineCases
{
    void ifWithoutBreak() {
        switch(hashCode()) {
        case 1:
            if (true) {
                System.lineSeparator();
            }
        case 2: // violation 'Fall\ through from previous branch of the switch statement.'
            System.lineSeparator();
            break;
        }
    }

    void noCommentAtTheEnd() {
        switch(hashCode()) {
        case 1: System.lineSeparator();

        case 2: // violation 'Fall\ through from previous branch of the switch statement.'
            System.lineSeparator();
            break;
        }
    }

    void synchronizedStatement() {
       switch (hashCode()) {
           case 1:
               synchronized (this) {
                   break;
               }
           case 2:
               // synchronized nested in if
               if (true) {
                   synchronized (this) {
                       break;
                   }
               } else {
                   synchronized (this) {
                       break;
                   }
               }
           case 3:
               synchronized (this) {
               }
               // fallthru
           default: // violation 'Fall\ through from previous branch of the switch statement.'
               break;
       }
    }

    void multipleCasesOnOneLine() {
        int i = 0;
        switch (i) {
        case 0: case 1: i *= i; // fall through
        case 2: case 3: i *= i; // violation 'Fall\ through from prev.* br.* switch statement.'
        case 4: case 5: i *= i; // violation 'Fall\ through from prev.* br.* switch statement.'
        case 6: case 7: i *= i; // violation 'Fall\ through from prev.* br.* switch statement.'
            break;
        default:
            throw new RuntimeException();
        }
    }
}
