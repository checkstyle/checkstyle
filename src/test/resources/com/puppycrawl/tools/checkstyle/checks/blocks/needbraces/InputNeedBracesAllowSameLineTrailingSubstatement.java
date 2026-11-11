/*
NeedBraces
allowSingleLineStatement = (default)false
allowEmptyLoopBody = (default)false
allowSameLineTrailingSubstatement = true
tokens = (default)LITERAL_DO, LITERAL_ELSE, LITERAL_FOR, LITERAL_IF, LITERAL_WHILE


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.needbraces;

public class InputNeedBracesAllowSameLineTrailingSubstatement {

    void simpleIf(int x) {
        if (x == 0) x = 1;
    }

    void elseIfChain(int x) {
        if      (x == 0) x = 1;
        else if (x == 1) x = 2;
        else if (x == 2) x = 3;
        else             x = 4;
    }

    void forWithInnerIf(int[] a) {
        for (int i = 0; i < 10; i++) if (i == 5) break;
    }

    void nestedLoopsAndIfs(int[] a) {
        for (int i = 0; i < 10; i++) if (i > 0) for (int j = 0; j < 10; j++) if (j > 0) break;
    }

    void foreachWithInnerIf(java.util.Set<String> set) {
        for (String s: set) if (s.isEmpty()) return;
    }

    void multilineElseIfBody(int x) {
        if      (x == 0) x = 1;
        // violation below ''if' construct must use '{}'s'
        else if (x == 1)
                         x = 2;
        else if (x == 2) x = 3;
        else             x = 4;
    }

    void multilineForWithInnerIf() {
        // violation below ''for' construct must use '{}'s'
        for (int i = 0; i < 10; i++)
            // violation below ''if' construct must use '{}'s'
            if (i == 5)
                break;
    }

    void whileSingleLine(Object o) {
        while (o != null) o.notify();
    }

    void multilineWhile(Object o) {
        // violation below ''while' construct must use '{}'s'
        while (o != null)
            o.notify();
    }

    void doWhileSingleLine(Object o) {
        do o.notify(); while (o != null);
    }

    void multilineDoWhile(Object o) {
        // violation below ''do' construct must use '{}'s'
        do
            o.notify();
        while (o != null);
    }

    void elseNotOnSameLine(int x) {
        if (x == 0) {
            x = 1;
        }
        // violation below ''else' construct must use '{}'s'
        else
            x = 2;
    }

    void multilineIfBody(int x) {
        // violation below ''if' construct must use '{}'s'
        if (x == 0)
            x = 1;
    }
}
