package com.puppycrawl.tools.checkstyle.checks.whitespace;

public class InputDoWhileWhitespaceAround {

    public void doWhileTest(int n) {
        int k = 0;
        do {
            ++k;
        } while(k < n);
    }
}
